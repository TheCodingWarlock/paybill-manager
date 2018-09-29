package com.thomaskioko.paybillmanager.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomaskioko.paybillmanager.domain.bills.GetBillById
import com.thomaskioko.paybillmanager.domain.bills.GetBills
import com.thomaskioko.paybillmanager.domain.model.Bill
import com.thomaskioko.paybillmanager.presentation.mapper.BillViewMapper
import com.thomaskioko.paybillmanager.presentation.model.BillView
import com.thomaskioko.paybillmanager.presentation.state.Resource
import com.thomaskioko.paybillmanager.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

open class GetBillsViewModel @Inject internal constructor(
        private val getBillById: GetBillById?,
        private val getBills: GetBills?,
        private val mapper: BillViewMapper
) : ViewModel() {

    private val billsLiveData: MutableLiveData<Resource<List<BillView>>> = MutableLiveData()
    private val billLiveData: MutableLiveData<Resource<BillView>> = MutableLiveData()


    override fun onCleared() {
        getBills?.dispose()
        getBillById?.dispose()
        super.onCleared()
    }

    open fun getBills(): LiveData<Resource<List<BillView>>> {
        billsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return billsLiveData
    }

    fun getBill(): LiveData<Resource<BillView>> {
        billsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return billLiveData
    }

    fun fetchBills() {
        getBills?.execute(BillsSubscriber())
    }


    fun fetchBillById(billId: String) {
        billLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getBillById?.execute(BillByIdSubscriber(), GetBillById.Params.forBill(billId))
    }


    inner class BillsSubscriber : DisposableObserver<List<Bill>>() {
        override fun onNext(t: List<Bill>) {
            billsLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) }, null))
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            billsLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

    inner class BillByIdSubscriber : DisposableObserver<Bill>() {
        override fun onNext(t: Bill) {
            billLiveData.postValue(Resource(ResourceState.SUCCESS, mapper.mapToView(t), null))
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            billLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

}