package com.teamteem.config;

import java.io.File;

/***
 * A class that holds paths for uploading videos.
 */
public final class UploadConfig {

    // TODO This value should be dynamically loaded from some external configuration file.
    public static File videosFolder = new File("/mnt/nfs_videos/");

    static {

        // Fallback for videos folder not existing.
        if (!videosFolder.exists()) {

            // Uncomment the below code to use a temporary directory instead of a proper location for storing videos.

            videosFolder = new File(System.getProperty("java.io.tmpdir"), "searchable-video-library");

        }

        videosFolder = new File(videosFolder, "videos");

        if (!videosFolder.exists()) {
            videosFolder.mkdirs();
        }


    }

}
