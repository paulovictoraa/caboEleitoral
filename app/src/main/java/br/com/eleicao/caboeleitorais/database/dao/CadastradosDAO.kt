package br.com.eleicao.caboeleitorais.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.eleicao.caboeleitorais.model.Eleitor

@Dao
interface CadastradosDAO {

    @Query("SELECT * FROM Eleitor")
    fun buscaTodos(): List<Eleitor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(eleitor: Eleitor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salvaTodos(eleitor: List<Eleitor>)

    @Query("SELECT * FROM Eleitor WHERE codigo = :id")
    fun buscaPorId(id: Long): LiveData<Eleitor>

}
