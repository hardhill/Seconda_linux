import java.util.Calendar

data class Time(
    val hour:Int,
    val minutes:Int,
    val seconds:Int
){
    companion object {
        fun getLatest():Time {
            val calendar = Calendar.getInstance()
            return Time(
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minutes = calendar.get(Calendar.MINUTE),
                seconds = calendar.get(Calendar.SECOND)
            )
        }
    }
}
