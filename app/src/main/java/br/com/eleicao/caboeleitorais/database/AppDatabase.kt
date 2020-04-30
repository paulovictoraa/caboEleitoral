package br.com.eleicao.caboeleitorais.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.eleicao.caboeleitorais.database.converter.ConversorBigDecimal
import br.com.eleicao.caboeleitorais.database.dao.PagamentoDAO
import br.com.eleicao.caboeleitorais.database.dao.CadastradosDAO
import br.com.eleicao.caboeleitorais.model.Pagamento
import br.com.eleicao.caboeleitorais.model.Eleitor

@Database(
    version = 2,
    entities = [Eleitor::class, Pagamento::class],
    exportSchema = false
)
@TypeConverters(ConversorBigDecimal::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): CadastradosDAO
    abstract fun pagamentoDao(): PagamentoDAO
}