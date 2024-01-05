package icu.lama.narchook;

public class CURLHacks {
    /**
     * Set to null to disable custom API-Legacy overriding.
     * If Legacy API is not been set and API-V2 set, all requests to API-Legacy will redirect to API-V2.
     *
     * @param url New API hostname
     */
    public static native void setCustomAPI(String url);

    /**
     * Set to null to disable custom API-V2 overriding.
     * @param url New API hostname
     */
    public static native void setCustomAPIV2(String url);
    public static native void toggleDisableSSLPinning(boolean status);

    /**
     * Ignore a specific curl option.
     * @param curlopt The curl option to ignore.
     */
    public static native void addDenyList(int curlopt);

    /**
     * Remove a specific curl option from the ignore list.
     * @param curlopt The curl option to remove.
     */
    public static native void removeDenyList(int curlopt);

    /**
     * Dump the client certificate.
     * Please run at least once the game and have at least once network request to dump the certificate.
     * @param path The output location.
     */
    public static native void dumpCert(String path);
    public static native void toggleFeature(boolean status);

    /**
     * Make all certificate trusted (Ex. self-signed certificate).
     * @param status True to trust all certificate.
     */
    public static native void approveAllCertificate(boolean status);
}
