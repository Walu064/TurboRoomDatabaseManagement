package pl.edu.wat.turboroomdatabasemanagement

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import pl.edu.wat.turboroomdatabasemanagement.databinding.ActivityMainBinding


@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: ArtAdapter
    private lateinit var base : SoldierDB
    private lateinit var dao: SoldierDao

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        base = SoldierDB.getSoldierDB(application.applicationContext)!!
        dao = base.soldierDao()
        GlobalScope.launch (Dispatchers.IO) {
            if (dao.getSoldierTableSize() == 0) {
                val initDbContent = getIniDbtContent(R.array.init_db_content)
                for (item in initDbContent) {
                    val index1 = item.indexOfFirst { it == ':' }
                    val index2 = item.indexOfLast { it == ':' }
                    val rank = item.substring(0, index1)
                    val name = item.substring(index1+1, index2)
                    val age = item.substring(index2+1, item.length)
                    dao.insert(Soldier(0,rank.uppercase(), name.uppercase(), age.toInt()))
                    Log.i("INDEX", "$index1:$rank:$name:$age")
                }
            }
            if (dao.getSoldierTableSize() > 0) {
                val b = dao.findSoldierByName("ARTUR")
                val c = dao.getSoldierTableSize()
                Log.i("PERSON", b[0].name)
                Log.i("SIZE", c.toString())
                val list = dao.getSoldiers()
                for (item in list) {
                    Log.i("LIST", item.id.toString())
                }
            }

            recyclerViewAdapter = ArtAdapter(dao.getSoldiers())
            binding.recyclerList.adapter = recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        binding.bDisplay.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val list = dao.getSoldiers()
                withContext(Dispatchers.Main) {
                    recyclerViewAdapter = ArtAdapter(list)
                    binding.recyclerList.adapter = recyclerViewAdapter
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }
        binding.bDisplayName.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val list =
                    dao.findSoldierByName(binding.eName.text.toString().uppercase())
                withContext(Dispatchers.Main) {
                    recyclerViewAdapter = ArtAdapter(list)
                    binding.recyclerList.adapter = recyclerViewAdapter
                }
            }
        }
    }

    private fun getIniDbtContent(id: Int): Array<String> {
        return resources.getStringArray(id)
    }
}