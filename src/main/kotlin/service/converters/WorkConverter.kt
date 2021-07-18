package service.converters

import model.Work
import okhttp3.Response

object WorkConverter : Converter<Work> {
    override fun convert(response: Response): Converter.Result<Work> {
        TODO("Not yet implemented")
    }
}