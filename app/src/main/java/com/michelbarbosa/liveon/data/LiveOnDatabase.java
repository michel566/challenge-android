package com.michelbarbosa.liveon.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.data.entities.OrderEntity;
import com.michelbarbosa.liveon.data.entities.OrderVehicleEntity;
import com.michelbarbosa.liveon.data.entities.StatusEntity;
import com.michelbarbosa.liveon.data.entities.UserEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;
import com.michelbarbosa.liveon.data.orders.VehicleDAO;
import com.michelbarbosa.liveon.data.user.UserDAO;

@Database(entities = {UserEntity.class, OrderEntity.class, StatusEntity.class, OrderVehicleEntity.class,
        VehicleEntity.class, ImageVehicleEntity.class}, version = 1, exportSchema = false)
public abstract class LiveOnDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public abstract VehicleDAO vehicleDAO();

    private static LiveOnDatabase INSTANCE;

    public static LiveOnDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LiveOnDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    LiveOnDatabase.class,
                                    "liveOn_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
