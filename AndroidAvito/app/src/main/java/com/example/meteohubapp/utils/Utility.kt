package com.example.meteohubapp.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import com.example.meteohubapp.R


/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class Utility {

    fun provideAlertDialog(context: Context, message: String): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton("Ок", null)
            .create()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }
/*
// Как делал prepopulate для базы данных. После это все аккуратно полегло в assets/app.db

    private fun loadToDb() {
        Thread {
            val db = (applicationContext as ApplicationResLocator).getRoomInstance()
            val cityListTmp2 = prepareCsv()
            db.cityDao().insertAll(cityListTmp2)

            Log.d("TAG", "loadToDb finished")
        }.start()
    }

// Обработка файла

    private fun prepareCsv(): List<City> {
        val cityListTmp = arrayListOf<City>()
        val `is` = resources.openRawResource(R.raw.cities_db_translated)

        try {
            BufferedReader(InputStreamReader(`is`, "UTF-8")).use { reader ->
                while (reader.ready()) {
                    val data = reader.readLine().split(";".toRegex()).toTypedArray()

                    cityListTmp.add(City(
                        Integer.valueOf(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5].toDouble(),
                        data[6].toDouble())
                    )
                }
            }
        } catch (e: IOException) {
            Log.e("TAG", "prepareCsv: $e")
        }
        return cityListTmp
    }

// Legacy версия метода из наработке по похожей задачи, где обходился без Sqlite (по сути Like expression)

public List<City> findCity(String cityName) {
        List<City> founded = new ArrayList<>();
        for (int i = 0; i < MainActivity.cityList.size(); i++) {
            if (MainActivity.cityList.get(i).getName().toLowerCase().contains(cityName.toLowerCase())) {
                founded.add(MainActivity.cityList.get(i));
            }
        }
        return founded;
    }
*/
}