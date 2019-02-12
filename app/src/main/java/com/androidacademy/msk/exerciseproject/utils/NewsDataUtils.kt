import android.util.Log
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.network.model.NetworkNewsItem
import com.androidacademy.msk.exerciseproject.utils.EMPTY

object NewsDataUtils {

    private const val DEBUG_DONT_MATCH_SIZE = "NewsDataUtils"

    fun getPreviewImageUrl(item: NetworkNewsItem): String {
        return if (item.multimedia.size > 1) {
            // Get the position of the best quality preview image.
            // It is the second position from the end of a list.
            val previewImagePosition = item.multimedia.size - 2
            item.multimedia[previewImagePosition].url ?: String.EMPTY
        } else {
            String.EMPTY
        }
    }

    fun getFullsizeImageUrl(item: NetworkNewsItem): String {
        return if (item.multimedia.size > 1) {
            // Get the position of the fullsize image. It is the latest position a list.
            val previewImagePosition = item.multimedia.size - 1
            item.multimedia[previewImagePosition].url ?: String.EMPTY
        } else {
            String.EMPTY
        }
    }

    fun setIds(news: List<DbNewsItem>, ids: IntArray): List<DbNewsItem> {
        if (news.size != ids.size) {
            Log.d(DEBUG_DONT_MATCH_SIZE, "news and ids don't match")
            return emptyList()
        }
        for (i in news.indices) {
            news[i].id = ids[i]
        }
        return news
    }
}