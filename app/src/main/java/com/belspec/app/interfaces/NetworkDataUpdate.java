package com.belspec.app.interfaces;

import com.belspec.app.retrofit.model.PoliceDepartmentItem;
import com.belspec.app.retrofit.model.PositionItem;
import com.belspec.app.retrofit.model.RankItem;
import com.belspec.app.utils.NetworkDataManager;

public interface NetworkDataUpdate {
    void onDefaultDataUpdate(NetworkDataManager netDataManager);
    void onRanksUpdate(RankItem rankList);
    void onPositionsUpdate(PositionItem positionList);
    void onPoliceDepartmentUpdate(PoliceDepartmentItem policeDepartmentList);
}
