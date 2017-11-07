package io.vinicius.androidcommon.util

class RegexUtil(pattern: String)
{
    companion object
    {
        /*
        Domain. It follows the pattern:

        1. It MUST start with a combination of alpha (CI) OR number.
        2. It MAY be followed by the characters '-'.
        3. It MUST end with a combination of alpha (CI) with AT LEAST 2 characters.
        */
        const val DOMAIN = "[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}"

        /*
        Domain with subdomains. It follows the pattern:

        1. It MUST start with a combination of alpha (CI) OR number.
        2. It MAY be followed by the characters '.', '-'.
        3. It MUST end with a combination of alpha (CI) with AT LEAST 2 characters.
        */
        const val DOMAIN_SUBDOMAIN = "[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

        /*
        URL. It follows the pattern:

        1. It MUST start with 'http://' OR 'https://'.
        2. It MUST end with @see *DOMAIN_SUBDOMAIN*.
        */
        const val URL = "https?:\\/\\/$DOMAIN_SUBDOMAIN"

        /*
        Email address. It follows the pattern:

        1. It MUST start with a combination of alpha (CI) OR number OR the characters '.', '_', '+', '-'.
        2. It MUST be followed by the character '@'.
        3. It MUST end with @see *DOMAIN*.
        */
        const val EMAIL = "[a-zA-Z0-9._+-]+@$DOMAIN_SUBDOMAIN"

        /*
        Username. It follows the pattern:

        1. It MUST start with a combination of alpha (CI).
        2. It MAY be followed by the characters '-', '_'.
        3. It MUST end with alpha (CI) OR number.
        */
        const val USERNAME = "[a-zA-Z]+(?:[_-]*[a-zA-Z0-9]+)*"
    }

    private val pattern = "^$pattern$"

    fun validate(string: String): Boolean
            = Regex(pattern).containsMatchIn(string)
}