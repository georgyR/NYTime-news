import android.util.Log
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.network.model.NetworkNewsItem

object NewsDataUtils {

    private const val DEBUG_DONT_MATCH_SIZE = "NewsDataUtils"

    //TODO remove String?

    fun getPreviewImageUrl(item: NetworkNewsItem): String? {
        if (item.multimedia.size > 1) {
            // Get the position of the best quality preview image.
            // It is the second position from the end of a list.
            val previewImagePosition = item.multimedia.size - 2
            return item.multimedia[previewImagePosition].url
        }
        return null
    }

    fun getFullsizeImageUrl(item: NetworkNewsItem): String? {
        if (item.multimedia.size > 1) {
            // Get the position of the fullsize image. It is the latest position a list.
            val previewImagePosition = item.multimedia.size - 1
            return item.multimedia[previewImagePosition].url
        }
        return null
    }

    fun setIds(news: List<DbNewsItem>, ids: IntArray): List<DbNewsItem>? {
        if (news.size != ids.size) {
            Log.d(DEBUG_DONT_MATCH_SIZE, "news and ids don't match")
            return null
        }
        for (i in news.indices) {
            news[i].id = ids[i]
        }
        return news
    }
}