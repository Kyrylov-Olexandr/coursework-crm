package com.crm.controllers.objects;

import com.crm.entities.BaseEntity;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import lombok.Getter;


public class Search<T extends BaseEntity> {

    private TableView<T> table;
    @Getter
    private FilteredList<T> filteredData;

    public Search(TableView<T> table) {
        this.table = table;
        this.filteredData = new FilteredList<>(table.getItems(), p -> true);
    }

    public void findByEntityFields(String searchText) {
        filteredData.setPredicate(entity -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return entity.toString().contains(searchText);
        });
    }

    public void findByFieldName(String fieldName, String searchValue) {
        filteredData.setPredicate(entity -> {
            if (searchValue== null || searchValue.isEmpty()) return true;
            return entity.toString().contains(fieldName + "=" + searchValue);
        });
    }
}
