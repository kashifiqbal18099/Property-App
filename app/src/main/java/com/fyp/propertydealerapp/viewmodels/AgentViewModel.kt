package com.fyp.propertydealerapp.viewmodels

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.fyp.propertydealerapp.model.AgentModel
import com.fyp.propertydealerapp.repostiories.AgentRepository
import com.fyp.propertydealerapp.repostiories.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AgentViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var agentRepository: AgentRepository
    var email: ObservableField<String>? = ObservableField<String>("")
    var phoneNumber: ObservableField<String>? = ObservableField<String>("")
    var firstName: ObservableField<String>? = ObservableField<String>("")
    var lastName: ObservableField<String>? = ObservableField<String>("")
    var salary: ObservableField<String>? = ObservableField<String>("")

    val formErrors = ObservableArrayList<AgentFormErros>()
    fun isFormValid(): Boolean {
        formErrors.clear()

        if (firstName?.get()?.isNullOrEmpty()!!) {
            formErrors.add(AgentFormErros.MISSING_FIRSTNAME)
        } else if (lastName?.get()?.isNullOrEmpty()!!) {
            formErrors.add(AgentFormErros.MISSING_LASTNAME)
        } else if (email?.get()?.isNullOrEmpty()!!) {
            formErrors.add(AgentFormErros.MISSING_EMAIL)
        } else if(phoneNumber?.get()?.isNullOrEmpty()!!) {
            formErrors.add(AgentFormErros.MISSING_PHONE)
        }
        else if(salary?.get()?.isNullOrEmpty()!!) {
            formErrors.add(AgentFormErros.MISSING_SALARY)
        }
        // all the other validation you require
        return formErrors.isEmpty()
    }

    init {
        var mAuth = FirebaseAuth.getInstance();
        var db = FirebaseFirestore.getInstance();
        agentRepository = AgentRepository(mAuth = mAuth, db = db);
    }

    fun addAgent(completion: () -> (Unit), onError: (error: String) -> (Unit)) {
        var agent = AgentModel()
        agent.email = email?.get()!!
        agent.firstName = firstName?.get()!!
        agent.lastName = lastName?.get()!!
        agent.password = "Agent@123"
        agent.phoneNumber = phoneNumber?.get()!!
        agent.salary  = salary?.get()!!

        agentRepository.addAgent(completion = completion, onError = onError, agent = agent)
    }


}

enum class AgentFormErros {
    MISSING_FIRSTNAME,
    MISSING_LASTNAME,
    MISSING_PHONE,
    MISSING_SALARY,
    MISSING_EMAIL,
}