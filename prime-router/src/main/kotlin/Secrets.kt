package gov.cdc.prime.router

import com.bettercloud.vault.Vault
import com.bettercloud.vault.VaultConfig

object Secrets {

    val vault: Vault

    init {
        vault = Vault(
            VaultConfig()
                .address(System.getenv("VAULT_ADDR") ?: "http://localhost:8200")
                .token(System.getenv("VAULT_TOKEN") ?: "myroot")
                .build(),
            2
        )
    }

    operator fun get(key: String): String {
        try {
            return System.getenv(key.toUpperCase())
                ?: System.getenv(key.replace(".", "__").replace('-', '_').toUpperCase())
                ?: vault.logical().read(System.getenv("VAULT_PATH") ?: "prime/local").getData().get(key) ?: ""
        } catch (e: Exception) {
            return ""
        }
    }

    operator fun get(path: String, key: String): String {
        try {
            return System.getenv("${path.toUpperCase()}__${key.toUpperCase()}")
                ?: System.getenv("${path.toUpperCase()}_${key.toUpperCase()}")
                ?: System.getenv("${path.toUpperCase().replace(".", "__").replace('-', '_').toUpperCase()}__${key.replace(".", "__").replace('-', '_').toUpperCase()}")
                ?: vault.logical().read((System.getenv("VAULT_PATH") ?: "prime/local") + "/" + path).getData().get(key) ?: ""
        } catch (e: Exception) {
            return ""
        }
    }

    val PRIME_ENVIRONMENT = Secrets["PRIME_ENVIRONMENT"]
}