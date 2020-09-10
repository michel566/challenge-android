package com.michelbarbosa.liveon.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = VehicleEntity.class, parentColumns = "pk_vehicle_id",
        childColumns = "fk_vehicle_id", onDelete = ForeignKey.CASCADE))
public class ImageVehicleEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pk_id")
    private int id;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "fk_vehicle_id", index = true)
    private int sourceId;

    public ImageVehicleEntity(int id, String imageUrl, int sourceId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.sourceId = sourceId;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getSourceId() {
        return sourceId;
    }
}
