package br.com.eleicao.caboeleitorais.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.eleicao.caboeleitorais.database.AppDatabase
import br.com.eleicao.caboeleitorais.model.Setor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class SetorWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val pdvType = object : TypeToken<List<Setor>>() {}.type
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open("setor.json")
            jsonReader = JsonReader(inputStream.reader())
            val setores: MutableList<Setor> = Gson().fromJson(jsonReader, pdvType)
            val database = AppDatabase.getInstance(applicationContext)
            val eleitorDao = database.setorDao()
            eleitorDao.salvaTodos(setores)
            Result.success()
        } catch (ex: Exception) {
            Log.e(this.javaClass.toString(), "Error worker contry", ex)
            Result.failure()
        } finally {
            jsonReader?.close()
        }
    }
}
