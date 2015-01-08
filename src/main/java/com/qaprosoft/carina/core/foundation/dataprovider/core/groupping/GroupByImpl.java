package com.qaprosoft.carina.core.foundation.dataprovider.core.groupping;

import java.util.*;

/**
 * Created by Patotsky on 29.12.2014.
 */

public class GroupByImpl {

    public static Object[][] getGroupedDataProviderArgs(Object[][] objects, int fieldNumber) {

        List<Object[]> listOfObjects = sortDefaultObject(objects, fieldNumber);
        Iterator iterator = listOfObjects.iterator();
        Object[] temp = (Object[]) iterator.next();
        List<List<Object[]>> ordered = new ArrayList<List<Object[]>>();
        List<Object[]> tempList = new ArrayList<Object[]>();
        tempList.add(temp);
        while (iterator.hasNext()) {
            Object[] current = (Object[]) iterator.next();
            if (temp[fieldNumber].equals(current[fieldNumber])) {
                tempList.add(current);

            } else {
                temp = current;
                ordered.add(tempList);
                tempList = new ArrayList<Object[]>();
                tempList.add(temp);
                if (!iterator.hasNext()){
                    ordered.add(tempList);
                }
            }
        }

        int arraySize = listOfObjects.get(0).length;
        Object[][] finalObject = new Object[ordered.size()][arraySize];


        int i = 0;
        for (List<Object[]> list : ordered) {
            finalObject[i][0] = list;
            i++;
        }

        return finalObject;

    }

    public static Object[][] getGroupedDataProviderMap(Object[][] objects, String fieldName) {

        List<Object[]> listOfObjects = sortMapObject(objects, fieldName);
        Iterator iterator = listOfObjects.iterator();
        Object[] temp = (Object[]) iterator.next();
        List<List<Object[]>> ordered = new ArrayList<List<Object[]>>();
        List<Object[]> tempList = new ArrayList<Object[]>();
        tempList.add(temp);
        while (iterator.hasNext()) {
            Object[] current = (Object[]) iterator.next();
            if (((HashMap) (temp[0])).get(fieldName).equals(((HashMap) (current[0])).get(fieldName))) {
                tempList.add(current);

            } else {
                temp = current;
                ordered.add(tempList);
                tempList = new ArrayList<Object[]>();
                tempList.add(temp);
                if (!iterator.hasNext()){
                    ordered.add(tempList);
                }
            }
        }

        int arraySize = listOfObjects.get(0).length;


        Object[][] finalObject = new Object[ordered.size()][arraySize];
        int i = 0;
        for (List<Object[]> list : ordered) {
            finalObject[i][0] = getHashMaps(list);

            if (arraySize > 1) {
                for (int j = 1; j < arraySize; j++) {
                    finalObject[i][j] = list.get(0)[j];
                    j++;
                }

            }
            i++;
        }

        return finalObject;

    }

    private static List<HashMap> getHashMaps(List<Object[]> list) {
        List<HashMap> hashMaps = new ArrayList<HashMap>();
        for (Object[] objects : list) {
            hashMaps.add((HashMap) objects[0]);
        }
        return hashMaps;
    }

    private static List<Object[]> sortDefaultObject(Object[][] objects, final int fieldNumber) {
        List<Object[]> listOfObjects = Arrays.asList(objects);
        Collections.sort(listOfObjects, new Comparator<Object[]>() {
            @Override
            public int compare(final Object[] object1, final Object[] object2) {
                String firstField = (String) object1[fieldNumber];
                String secondField = (String) object2[fieldNumber];
                return firstField.compareTo(secondField);
            }
        });
        return listOfObjects;
    }

    private static List<Object[]> sortMapObject(Object[][] objects, final String keyName) {
        List<Object[]> listOfObjects = Arrays.asList(objects);
        Collections.sort(listOfObjects, new Comparator<Object[]>() {
            @Override
            public int compare(final Object[] object1, final Object[] object2) {
                String firstField = ((HashMap<String, String>) object1[0]).get(keyName);
                String secondField = ((HashMap<String, String>) object2[0]).get(keyName);
                return firstField.compareTo(secondField);
            }
        });
        return listOfObjects;
    }

}
