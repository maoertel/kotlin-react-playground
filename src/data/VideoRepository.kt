package data

import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import model.Video
import kotlin.browser.window

interface VideoRepository {
    suspend fun fetchVideos(numberOfVideos: Int = 25): List<Video>
}

class VideoRepositoryImpl : VideoRepository {

    private suspend fun fetchVideo(id: Int): Video = window.fetch("$url$id")
            .await()
            .json()
            .await()
            .unsafeCast<Video>()

    override suspend fun fetchVideos(numberOfVideos: Int): List<Video> = coroutineScope {
        (1..if (numberOfVideos > maxNumberOfVideos) maxNumberOfVideos else numberOfVideos)
                .map { id -> async { fetchVideo(id) } }
                .awaitAll()
    }

    companion object {
        private const val url = "https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/"
        private const val maxNumberOfVideos = 25
    }

}