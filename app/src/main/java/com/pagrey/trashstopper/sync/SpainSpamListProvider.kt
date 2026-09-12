package com.pagrey.trashstopper.sync

import android.content.Context
import com.pagrey.trashstopper.data.NumberEntity
import com.pagrey.trashstopper.reputation.ReputationSnapshotPayload
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpainSpamListProvider : ReputationSnapshotProvider {
    companion object {
        private const val SOURCE_URL = "https://" + "raw.githubusercontent.com/" + "mv12star/lista-telefonos-spam/main/lista_numeros_spam.txt"
        private const val MAX_BYTES = 2_000_000
    }

    override suspend fun fetch(context: Context): ReputationSnapshotPayload = withContext(Dispatchers.IO) {
        val connection = (URL(SOURCE_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 10_000
            readTimeout = 20_000
            setRequestProperty("User-Agent", "TrashStopper/0.1 (+PAGREY)")
            setRequestProperty("Accept", "text/plain")
        }
        try {
            check(connection.responseCode in 200..299) { "Reputation source returned HTTP ${connection.responseCode}" }
            check(connection.contentLengthLong in -1..MAX_BYTES.toLong()) { "Reputation source is unexpectedly large" }
            val bytes = connection.inputStream.use { input -> input.readBytes().also { check(it.size <= MAX_BYTES) { "Reputation source exceeds size limit" } } }
            val numbers = bytes.toString(Charsets.UTF_8).lineSequence()
                .map { PhoneNumberNormalizer.normalize(it) }
                .filter { it.matches(Regex("\\d{9}")) }
                .distinct()
                .flatMap { local -> sequenceOf(numberEntity(local), numberEntity("+34$local")) }
                .toList()
            check(numbers.isNotEmpty()) { "Reputation source contained no valid numbers" }
            ReputationSnapshotPayload(sha256(bytes), numbers)
        } finally { connection.disconnect() }
    }

    private fun numberEntity(number: String) = NumberEntity(
        phoneNumber = number,
        country = "ES",
        category = "SPAM",
        riskScore = 80,
        reportCount = 1,
        verified = false
    )

    private fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
}
