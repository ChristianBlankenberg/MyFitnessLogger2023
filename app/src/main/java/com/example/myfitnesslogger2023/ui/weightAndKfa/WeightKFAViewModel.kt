package com.example.myfitnesslogger2023.ui.weightAndKfa

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfitnesslogger.businesslogic.dataGate
import com.example.myfitnesslogger2023.ui.baseClasses.BaseViewModel
import com.example.myfitnesslogger2023.utils.firebaseFireStoreService
import com.example.myfitnesslogger2023.utils.globalVariables
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime

class WeightKFAViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun sheetsTest(onSuccessCallback: (result : Any) -> Unit) {

        /*
        GlobalScope.launch {
            val value = dataGate.getValue(globalVariables.URL, "data", 1, 1)

            activity?.runOnUiThread {
                onSuccessCallback(value)
            }
        }
        */
    }

    fun fireStoreTest() {

        val firebaseUtils = firebaseFireStoreService()

        // create a dummy data
        val data = hashMapOf<String, Any>(
            "name" to "John doe",
            "city" to "Nairobi",
            "age" to 24
        )

       /* firebaseUtils.addData("users", "database", data)
*/
/*            var receivedData : HashMap<String, Any>

            firebaseUtils.getData(
                "users",
                arrayListOf<String>( "name", "city", "age"),
                {
                    onDataReceived(it)
                },
                {

                }
                )
  */
        /*
        firebaseUtils.modifyData(
            "users",
            hashMapOf(
                "age" to 25,
                "name" to FieldValue.serverTimestamp()
            )
        )
        */
    }

    fun onDataReceived(data: HashMap<String, Any>) {

    }

    fun sendData(
        weightGreat : Int,
        weightSmall : Int,
        kfaGreat : Int,
        kfaSmall : Int,
        hasKFAValue : Boolean,
        fragmentActivity : FragmentActivity? = null)
    {
        dataGate.sendWeight(weightGreat + weightSmall / 10.0, LocalDateTime.now(), fragmentActivity)
        if (hasKFAValue)
        {
            dataGate.sendKFA(kfaGreat + kfaSmall / 10.0, LocalDateTime.now(), fragmentActivity)
        }
    }

    fun getPastKFA(deltaDays: Int, fragmentActivity : FragmentActivity? = null): Double?
    {
        return dataGate.getKFA(LocalDateTime.now().plusDays(deltaDays.toLong()), fragmentActivity)
    }

    fun getPastWeight(deltaDays: Int, fragmentActivity : FragmentActivity? = null): Double? {
        return dataGate.getWeight(LocalDateTime.now().plusDays(deltaDays.toLong()), fragmentActivity)
    }

}