package io.vinicius.androidcommon.util

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.preference.PreferenceManager
import android.text.TextUtils

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList
import java.util.Arrays

import timber.log.Timber

class TinyDb(appContext: Context)
{
    private val preferences: SharedPreferences
    private var DEFAULT_APP_IMAGEDATA_DIRECTORY: String? = null
    private var lastImagePath = ""

    init
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    fun getImage(path: String): Bitmap?
    {
        var theGottenBitmap: Bitmap? = null

        try {
            theGottenBitmap = BitmapFactory.decodeFile(path)
        } catch (e: Exception) {
            Timber.e(e, "Error getting bitmap")
        }

        return theGottenBitmap
    }

    /**
     * Returns the String path of the last image that was saved with this Object
     */
    val savedImagePath: String
        get() = lastImagePath

    /**
     * Returns the String path of the last image that was saved with this tnydbobj
     *
     * @param theFolder the theFolder - the folder path dir you want to save it to e.g "DropBox/WorkImages"
     * @param theImageName the theImageName - the name you want to assign to the image file e.g "MeAtlunch.png"
     */
    fun putImagePNG(theFolder: String, theImageName: String, theBitmap: Bitmap): String?
    {
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder
        val mFullPath = setupFolderPath(theImageName)
        saveBitmapPNG(mFullPath, theBitmap)
        lastImagePath = mFullPath
        return mFullPath
    }

    private fun setupFolderPath(imageName: String): String
    {
        val sdcardPath = Environment.getExternalStorageDirectory()
        val mFolder = File(sdcardPath, DEFAULT_APP_IMAGEDATA_DIRECTORY!!)

        if (!mFolder.exists())
            if (!mFolder.mkdirs())
                Timber.e("Default Save Path Creation Error")

        return mFolder.path + '/' + imageName
    }

    private fun saveBitmapPNG(strFileName: String?, bitmap: Bitmap?): Boolean
    {
        if (strFileName == null || bitmap == null)
            return false

        var bSuccess1 = false
        var bSuccess2: Boolean
        var bSuccess3: Boolean
        val saveFile = File(strFileName)

        if (saveFile.exists()) {
            if (!saveFile.delete())
                return false
        }

        try {
            bSuccess1 = saveFile.createNewFile()
        } catch (e: IOException) {
            Timber.e(e, "Error saving bitmap")
        }

        var out: OutputStream? = null

        try {
            out = FileOutputStream(saveFile)
            bSuccess2 = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } catch (e: Exception) {
            Timber.e(e, "Error saving bitmap")
            bSuccess2 = false
        }

        try {
            if (out != null) {
                out.flush()
                out.close()
                bSuccess3 = true
            } else
                bSuccess3 = false
        } catch (e: IOException) {
            e.printStackTrace()
            bSuccess3 = false
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        return bSuccess1 && bSuccess2 && bSuccess3
    }

    fun getInt(key: String): Int = preferences.getInt(key, 0)

    fun getLong(key: String): Long = preferences.getLong(key, 0L)

    fun getString(key: String): String = preferences.getString(key, "")

    fun getDouble(key: String): Double
    {
        val number = getString(key)
        return number.toDouble()
    }

    fun putInt(key: String, value: Int)
    {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun putLong(key: String, value: Long)
    {
        val editor = preferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun putDouble(key: String, value: Double)
    {
        putString(key, value.toString())
    }

    fun putString(key: String, value: String)
    {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putList(key: String, marray: ArrayList<String>)
    {
        val editor = preferences.edit()
        val mystringlist = marray.toTypedArray()

        // the comma like character used below is not a comma it is the SINGLE
        // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
        // seprating the items in the list
        editor.putString(key, TextUtils.join("‚‗‚", mystringlist))
        editor.apply()
    }

    fun getList(key: String): ArrayList<String>
    {
        // the comma like character used below is not a comma it is the SINGLE
        // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
        // seprating the items in the list
        val mylist = TextUtils.split(preferences.getString(key, ""), "‚‗‚")
        return ArrayList(Arrays.asList(*mylist))
    }

    fun putListInt(key: String, marray: ArrayList<Int>)
    {
        val editor = preferences.edit()
        val mystringlist = marray.toTypedArray()
        // the comma like character used below is not a comma it is the SINGLE
        // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
        // seprating the items in the list
        editor.putString(key, TextUtils.join("‚‗‚", mystringlist))
        editor.apply()
    }

    fun getListInt(key: String): ArrayList<Int>
    {
        // the comma like character used below is not a comma it is the SINGLE
        // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
        // seprating the items in the list
        val mylist = TextUtils.split(preferences.getString(key, ""), "‚‗‚")
        val gottenlist = ArrayList(Arrays.asList(*mylist))
        val gottenlist2 = ArrayList<Int>()

        for (i in gottenlist.indices)
            gottenlist2.add(Integer.parseInt(gottenlist[i]))

        return gottenlist2
    }

    fun putListBoolean(key: String, marray: ArrayList<Boolean>)
    {
        val origList = ArrayList<String>()

        for (b in marray) {
            if (b)
                origList.add("true")
            else
                origList.add("false")
        }

        putList(key, origList)
    }

    fun getListBoolean(key: String): ArrayList<Boolean>
    {
        val origList = getList(key)
        val mBools = ArrayList<Boolean>()

        for (b in origList) {
            if (b == "true")
                mBools.add(true)
            else
                mBools.add(false)
        }

        return mBools
    }

    fun putBoolean(key: String, value: Boolean)
    {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)

    fun getBoolean(key: String, value: Boolean): Boolean = preferences.getBoolean(key, true)

    fun putFloat(key: String, value: Float)
    {
        val editor = preferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String): Float = preferences.getFloat(key, 0f)

    fun remove(key: String)
    {
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear()
    {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    val all: Map<String, *>
        get() = preferences.all

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}