package com.belspec.app.interfaces;

import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.Position;
import com.belspec.app.retrofit.model.Rank;
import com.belspec.app.retrofit.model.RoadLawPoint;
import com.belspec.app.utils.NetworkDataManager;

import java.util.List;

public interface NetworkDataUpdate {
    void onDefaultDataUpdate(NetworkDataManager netDataManager);
    void onRanksUpdate(NetworkDataManager netDataManager);
    void onPositionsUpdate(NetworkDataManager netDataManager);
    void onPoliceDepartmentUpdate(NetworkDataManager netDataManager);
    void onRoadLowPointUpdate(NetworkDataManager netDataManager);
}
