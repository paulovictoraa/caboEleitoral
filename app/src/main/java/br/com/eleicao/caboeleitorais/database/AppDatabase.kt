package br.com.eleicao.caboeleitorais.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.eleicao.caboeleitorais.database.converter.ConversorBigDecimal
import br.com.eleicao.caboeleitorais.database.dao.EleitorDAO
import br.com.eleicao.caboeleitorais.database.dao.SetorDAO
import br.com.eleicao.caboeleitorais.model.Setor
import br.com.eleicao.caboeleitorais.model.eleitor.EleitorPersistence
import br.com.eleicao.caboeleitorais.worker.SetorWorker

@Database(
    version = 4,
    entities = [EleitorPersistence::class, Setor::class],
    exportSchema = false
)
@TypeConverters(ConversorBigDecimal::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eleitorDao(): EleitorDAO
    abstract fun setorDao(): SetorDAO

    companion object {
        private const val NOME_BANCO_DE_DADOS = "caboeleitorais.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val workManager = WorkManager.getInstance(context)
                        workManager.enqueue(OneTimeWorkRequest.from(SetorWorker::class.java))
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}