package com.michelbarbosa.liveon.mapper;

import com.michelbarbosa.liveon.api.response.OrderDetailsResponse;
import com.michelbarbosa.liveon.api.response.SignatureDetailsResponse;
import com.michelbarbosa.liveon.api.response.SummaryValuesResponse;
import com.michelbarbosa.liveon.api.response.UserProfileResponse;
import com.michelbarbosa.liveon.api.response.VehicleDetailsResponse;
import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;
import com.michelbarbosa.liveon.domain.OrderDetailsVehicle;
import com.michelbarbosa.liveon.data.entities.OrderEntity;
import com.michelbarbosa.liveon.data.entities.OrderVehicleEntity;
import com.michelbarbosa.liveon.data.entities.StatusEntity;
import com.michelbarbosa.liveon.data.entities.UserEntity;
import com.michelbarbosa.liveon.data.entities.VehicleEntity;
import com.michelbarbosa.liveon.domain.Order;
import com.michelbarbosa.liveon.domain.OrderVehicle;
import com.michelbarbosa.liveon.domain.Status;
import com.michelbarbosa.liveon.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiveOnMappers {

    public static String getToken(UserProfileResponse response) {
        if (response != null) {
            if (response.getToken() != null) {
                return response.getToken();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static UserEntity userMapperToEntity(UserProfileResponse response) {
        if (response != null) {
            return new UserEntity(
                    1,
                    response.getAvatar_url(),
                    response.getUsername(),
                    response.getFullname(),
                    response.getCity(),
                    response.getState_abbr()
            );
        } else {
            return null;
        }
    }

    public static User userEntityToDomain(UserEntity entity,
                                          List<OrderEntity> orderEntityList,
                                          List<StatusEntity> statusEntityList) {
        List<Order> orderList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList) {
            List<Status> statusList = new ArrayList<>();

            for (StatusEntity statusEntity : statusEntityList) {
                if (statusEntity.getOrderId() == orderEntity.getOrderId()) {
                    Status status = new Status(statusEntity.getCode(), statusEntity.getLabel(),
                            statusEntity.isChecked(), orderEntity.getOrderId());
                    statusList.add(status);
                }
            }
            Order order = new Order(
                    orderEntity.getOrderId(),
                    orderEntity.getSubmodel_name(),
                    statusList
            );
            orderList.add(order);
        }

        return new User(entity.getUrlPictureAvatar(),
                entity.getUsername(),
                entity.getFullname(),
                entity.getCity(),
                entity.getUf(),
                orderList);
    }

    public static List<OrderVehicle> orderEntityToOrderVehicleEntity(
            List<OrderVehicleEntity> orderEntityList) {
        List<OrderVehicle> orderVehicleList = new ArrayList<>();
        for (OrderVehicleEntity entity : orderEntityList) {
            orderVehicleList.add(new OrderVehicle(
                    entity.getOrderId(), entity.getSubmodel_name()));
        }
        return orderVehicleList;
    }

    public static VehicleEntity vehicleMapperToEntity(OrderDetailsResponse response, int orderId) {
        if (response != null) {
            VehicleDetailsResponse vehicle = response.getVehicleDetailsResponse();
            SignatureDetailsResponse signature = response.getSignatureDetailsResponse();
            SummaryValuesResponse summary = response.getSummaryValuesResponse();

            return new VehicleEntity(new Random().nextInt(10000),
                    vehicle.getVehicleBrand(),
                    vehicle.getEngineType(),
                    vehicle.getKm(),
                    vehicle.getEngine(),
                    vehicle.getVehicleYear(),
                    vehicle.getVehicleModel(),
                    vehicle.getDeliveryDelay(),
                    vehicle.getFuelType(),
                    vehicle.getDoorsQtd(),
                    signature.getPlanType(),
                    signature.getMonths(),
                    signature.getAdditionalFranchise(),
                    summary.getMonthlyPrice(),
                    summary.getTotalPrice(),
                    summary.getExtras(),
                    orderId);
        } else {
            return null;
        }
    }

    public static List<ImageVehicleEntity> imageVehicleListToEntity(OrderDetailsResponse response,
                                                                    int idVehicleEntity) {
        List<ImageVehicleEntity> list = new ArrayList<>();
        int i = 0;
        if (response != null) {
            for (String urlImage : response.getVehicleDetailsResponse().getImageUrl()) {
                list.add(new ImageVehicleEntity(i++, urlImage, idVehicleEntity));
            }
            return list;
        } else {
            return null;
        }

    }

    public static List<OrderDetailsVehicle> generateOrderDetailsVehicleList(
            List<VehicleEntity> vehicleList, List<ImageVehicleEntity> imageList) {
        List<OrderDetailsVehicle> orderList = new ArrayList<>();

        for (VehicleEntity vehicleItem : vehicleList) {
            List<ImageVehicleEntity> newImageList = new ArrayList<>();
            for (ImageVehicleEntity imageItem : imageList) {
                if (imageItem.getSourceId() == vehicleItem.getId()) {
                    newImageList.add(imageItem);
                }
            }

            orderList.add(new OrderDetailsVehicle(vehicleItem, newImageList));
        }

        return orderList;
    }

    public static OrderDetailsVehicle getOrderDetailsVehicle(
            List<OrderDetailsVehicle> orderDetailsVehicleList, int orderId) {
        if (orderDetailsVehicleList != null) {
            OrderDetailsVehicle orderDetailsVehicle = null;

            for (OrderDetailsVehicle order : orderDetailsVehicleList) {
                if (order.getEntity().getOrderId() == orderId) {
                    orderDetailsVehicle = order;
                }
            }
            return orderDetailsVehicle;
        } else {
            return null;
        }
    }


}
