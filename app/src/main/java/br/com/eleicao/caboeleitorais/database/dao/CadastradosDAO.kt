package br.com.eleicao.caboeleitorais.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.eleicao.caboeleitorais.model.Eleitor

@Dao
interface CadastradosDAO {

    @Query("SELECT * FROM Eleitor")
    fun buscaTodos(): LiveData<List<Eleitor>>

    @Insert
    fun salva(vararg eleitor: Eleitor)

    @Query("SELECT * FROM Eleitor WHERE id = :id")
    fun buscaPorId(id: Long): LiveData<Eleitor>

}
