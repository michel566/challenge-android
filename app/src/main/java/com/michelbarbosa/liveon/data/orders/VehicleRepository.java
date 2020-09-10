package com.michelbarbosa.liveon.data.orders;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.michelbarbosa.liveon.data.LiveOnDatabase;
import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.domain.OrderDetailsVehicle;
import com.michelbarbosa.liveon.data.entities.OrderVehicleEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;
import com.michelbarbosa.liveon.mapper.LiveOnMappers;

import java.util.List;

class VehicleRepository {

    private MutableLiveData<List<OrderVehicleEntity>> mOrderVListResult = new MutableLiveData<>();
    private LiveData<List<OrderVehicleEntity>> mOrderVList;
    private MutableLiveData<List<OrderDetailsVehicle>> mOrderDetailsListResult = new MutableLiveData<>();
    private LiveData<List<ImageVehicleEntity>> mImageVehicleList;
    private LiveData<List<VehicleEntity>> mVehicleList;

    private VehicleDAO mDao;

    VehicleRepository(Application application) {
        LiveOnDatabase db;
        db = LiveOnDatabase.getDatabase(application);
        mDao = db.vehicleDAO();
        mOrderVList = mDao.getOrderVehicleEntityList();
        mImageVehicleList = mDao.getImageVehicleList();
        mVehicleList = mDao.getVehicleEntityList();
    }

    /**
     * INSERT - List<OrderVehicleEntity>
     */
    void insertOrderVEntityList(List<OrderVehicleEntity> orderVehicleEntityList) {
        InsertOrderEntityListAsyncTask task = new InsertOrderEntityListAsyncTask(mDao);
        task.execute(orderVehicleEntityList);
    }

    private static class InsertOrderEntityListAsyncTask extends AsyncTask<List<OrderVehicleEntity>, Void, Void> {
        private VehicleDAO asyncTaskDao;

        InsertOrderEntityListAsyncTask(VehicleDAO asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(List<OrderVehicleEntity>... lists) {
            asyncTaskDao.insertOrderVehicleEntity(lists[0]);
            return null;
        }
    }

    /**
     * SELECT - List<OrderVehicleEntity>
     **/

    LiveData<List<OrderVehicleEntity>> getmOrderVList() {
        SelectOrderVehicleListAsyncTask task = new SelectOrderVehicleListAsyncTask(mDao);
        task.delegate = this;
        task.execute();
        return mOrderVList;
    }

    MutableLiveData<List<OrderVehicleEntity>> getmOrderVListResult() {
        SelectOrderVehicleListAsyncTask task = new SelectOrderVehicleListAsyncTask(mDao);
        task.delegate = this;
        task.execute();
        return mOrderVListResult;
    }

    private void asyncFinishedOrderList(List<OrderVehicleEntity> orderVehicleEntityList) {
        mOrderVListResult.postValue(orderVehicleEntityList);
    }

    private static class SelectOrderVehicleListAsyncTask extends AsyncTask<Void, Void, List<OrderVehicleEntity>> {
        private VehicleDAO asyncTaskDao;
        private VehicleRepository delegate = null;

        SelectOrderVehicleListAsyncTask(VehicleDAO asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<OrderVehicleEntity> doInBackground(Void... voids) {
            return asyncTaskDao.findOrderVehicleEntityList();
        }

        @Override
        protected void onPostExecute(List<OrderVehicleEntity> orderVehicleEntityList) {
            delegate.asyncFinishedOrderList(orderVehicleEntityList);
        }
    }

    /**
     * INSERT - VehicleEntity
     */

    void insertVehicleDetail(OrderDetailsVehicle orderDetailsVehicle) {
        InsertVehicleEntityAsyncTask task = new InsertVehicleEntityAsyncTask(mDao);
        task.execute(orderDetailsVehicle);
    }

    private static class InsertVehicleEntityAsyncTask extends AsyncTask<OrderDetailsVehicle, Void, Void> {
        private VehicleDAO asyncTaskDao;

        InsertVehicleEntityAsyncTask(VehicleDAO asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(OrderDetailsVehicle... orders) {
            asyncTaskDao.insertVehicleEntity(orders[0].getEntity());
            asyncTaskDao.insertImageListEntity(orders[0].getImages());
            return null;
        }

    }

    /**
     * SELECT - List<OrderDetailsVehicle>
     **/

    LiveData<List<ImageVehicleEntity>> getmImageVehicleList() {
        return mImageVehicleList;
    }

    LiveData<List<VehicleEntity>> getmVehicleList() {
        return mVehicleList;
    }

    MutableLiveData<List<OrderDetailsVehicle>> getmOrderDetailsListResult() {
        SelectOrderDetailsVehicleAsyncTask task = new SelectOrderDetailsVehicleAsyncTask(mDao);
        task.delegate = this;
        task.execute();
        return mOrderDetailsListResult;
    }

    private void asyncFinishedOrderVehicleList(List<OrderDetailsVehicle> orderVehicleEntityList) {
        mOrderDetailsListResult.postValue(orderVehicleEntityList);
    }

    private static class SelectOrderDetailsVehicleAsyncTask extends AsyncTask<Void, Void, List<OrderDetailsVehicle>> {
        private VehicleDAO asyncTaskDao;
        private VehicleRepository delegate = null;

        SelectOrderDetailsVehicleAsyncTask(VehicleDAO asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<OrderDetailsVehicle> doInBackground(Void... voids) {
            List<ImageVehicleEntity> imageList = asyncTaskDao.findImageVehicleList();
            List<VehicleEntity> vehicleList = asyncTaskDao.findVehicleEntityList();
            return LiveOnMappers.generateOrderDetailsVehicleList(vehicleList, imageList);
        }

        @Override
        protected void onPostExecute(List<OrderDetailsVehicle> orderVehicleEntityList) {
            delegate.asyncFinishedOrderVehicleList(orderVehicleEntityList);
        }
    }


}
