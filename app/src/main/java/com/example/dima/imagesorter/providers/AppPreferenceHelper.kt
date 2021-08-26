package com.example.dima.imagesorter.providers

import android.content.Context
import android.content.SharedPreferences
import com.example.dima.imagesorter.di.PreferenceInfo
import com.example.dima.imagesorter.util.log
import javax.inject.Inject
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class AppPreferenceHelper @Inject constructor(context: Context,
                                              @PreferenceInfo private val prefFileName: String)
                                    : PreferenceHelper {

    companion object {
        private val PREF_KEY_ITEMS_POOLING = "PREF_KEY_USER_LOGGED_IN_MODE"
    }

    private val mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    private val prefSubject = BehaviorSubject.createDefault(mPrefs)

    private val prefChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, _ ->
        //prefSubject.onNext(sharedPreferences)
        onNextWithLog(sharedPreferences)
    }

    fun onNextWithLog(sharedPreferences: SharedPreferences){
        prefSubject.onNext(sharedPreferences)
        "prefSubject.OnNext() called in AppPreferenceHelper module!".log()
    }

    init {
        mPrefs.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }

//    override fun getItemsPooling(): Int? =  mPrefs.getInt(PREF_KEY_ITEMS_POOLING, 0)
//
//    override fun setItemsPooling(poolingType: Int?) {
//        with (mPrefs.edit()) {
//            putInt(PREF_KEY_ITEMS_POOLING, poolingType?:0)
//            apply()
//            //P.S.
//            //apply() changes the in-memory SharedPreferences object immediately but writes the
//            // updates to disk asynchronously. Alternatively, you can use commit() to write the data
//            // to disk synchronously. But because commit() is synchronous, you should avoid calling
//            // it from your main thread because it could pause your UI rendering.
//        }
//
//    }





    override fun setItemsPooling(poolingType: Int?): Completable = prefSubject
            .firstOrError()
            .editSharedPreferences {
                putInt(PREF_KEY_ITEMS_POOLING, poolingType?:0)
                "Pooling type edited to $poolingType in AppPreferenceHelper module!".log()
            }

    override fun getItemsPooling(): Observable<Int?> = prefSubject
            .map { it.getInt(PREF_KEY_ITEMS_POOLING, 0) }


    override fun clear(): Completable {
        return prefSubject.firstOrError()
                .clearSharedPreferences {
                    remove(PREF_KEY_ITEMS_POOLING)
                }
    }

    fun Single<SharedPreferences>.editSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
        flatMapCompletable {
            Completable.fromAction {
                it.edit().also(batch).apply()
            }
        }

    fun Single<SharedPreferences>.clearSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
        flatMapCompletable {
            Completable.fromAction {
                it.edit().also(batch).apply()
            }
        }
}