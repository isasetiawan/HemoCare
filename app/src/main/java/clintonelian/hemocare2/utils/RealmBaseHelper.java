package clintonelian.hemocare2.utils;

import android.content.Context;

import java.util.Date;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.Sort;
import io.realm.exceptions.RealmException;


public class RealmBaseHelper {

    public void setRealmInit(final Context context) {

        Realm.init(context);
        Realm.setDefaultConfiguration(new RealmConfiguration
                .Builder()
                .name("HemoCare")
                .deleteRealmIfMigrationNeeded()
                .build());

    }

    public <E extends RealmObject> List<E> getAll(final Realm realm, final Class<E> objectClass) {
        return realm.where(objectClass).findAll();
    }

    public <E extends RealmObject> List<E> getAllWhereNot(final Realm realm, final Class<E> objectClass,
                                                       final String columnSearch, final String keyWord) {
        return realm.where(objectClass).notEqualTo(columnSearch,keyWord).findAll();
    }

    public <E extends RealmObject> List<E> getAllWhereNot(final Realm realm, final Class<E> objectClass,
                                                          final String columnSearch, final String keyWord,
                                                          final String columnSearch2, final String keyWord2) {
        return realm.where(objectClass).notEqualTo(columnSearch,keyWord).equalTo(columnSearch2,keyWord2).findAll();
    }

    public <E extends RealmObject> List<E> getAllWhereNotOr(final Realm realm, final Class<E> objectClass,
                                                          final String columnSearch, final String keyWord,final String keyWordOr,
                                                          final String columnSearch2, final String keyWord2) {
        return realm.where(objectClass).equalTo(columnSearch2,keyWord2)
                .notEqualTo(columnSearch,keyWord)
                .notEqualTo(columnSearch,keyWordOr)
                .findAll();
    }

    public <E extends RealmObject> List<E> getAllWhere(final Realm realm, final Class<E> objectClass,
                                                          final String columnSearch, final String keyWord) {
        return realm.where(objectClass).equalTo(columnSearch,keyWord).findAll();
    }

    public <E extends RealmObject> List<E> getAllWhere(final Realm realm, final Class<E> objectClass,
                                                       final String columnSearch, final String keyWord,
                                                       final String columnSearch2, final String keyWord2) {
        return realm.where(objectClass).equalTo(columnSearch,keyWord).equalTo(columnSearch2,keyWord2).findAll();
    }

    public <E extends RealmObject> List<E> getAllWhereOr(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final String keyWord,final String keyWordOr,
                                                       final String columnSearch2, final String keyWord2) {
        return realm.where(objectClass)
                .equalTo(columnSearch2,keyWord2,Case.INSENSITIVE)
                .equalTo(columnSearch,keyWord,Case.INSENSITIVE)
                .or()
                .equalTo(columnSearch,keyWordOr,Case.INSENSITIVE)
                .findAll();
    }

    public <E extends RealmObject> List<E> getAllWhereSorted(final Realm realm, final Class<E> objectClass,
                                                             final String columnSearch, final String keyWord,
                                                             final String columnSearch2, final String keyWord2,
                                                             final String fieldName, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).equalTo(columnSearch,keyWord).equalTo(columnSearch2,keyWord2).findAllSorted(fieldName, sort);
    }

    public <E extends RealmObject> List<E> getAllWhereSorted(final Realm realm, final Class<E> objectClass,
                                                             final String columnSearch, final String keyWord,
                                                             final String fieldName, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).equalTo(columnSearch,keyWord).findAllSorted(fieldName, sort);
    }


    public <E extends RealmObject> List<E> getAllSorted(final Realm realm, final Class<E> objectClass,
                                                        final String fieldName, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).findAllSorted(fieldName, sort);
    }

    public <E extends RealmObject> long countAll(final Realm realm, final Class<E> objectClass) {
        return realm.where(objectClass).count();
    }

    public <E extends RealmObject> long countAllWhere(final Realm realm, final Class<E> objectClass,
                                                      final String columnSearch, final String keyWord) {
        return realm.where(objectClass).equalTo(columnSearch, keyWord, Case.INSENSITIVE).count();
    }

    public <E extends RealmObject> long countAllWhereAnd(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final boolean keyWord) {
        return realm.where(objectClass).equalTo(columnSearch, keyWord).count();
    }

    public <E extends RealmObject> long countAllWhereAnd(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch1, final boolean keyWord1,
                                                         final String columnSearch2, final long keyWord2) {
        return realm.where(objectClass).equalTo(columnSearch1, keyWord1)
                .equalTo(columnSearch2, keyWord2).count();
    }

    public <E extends RealmObject> E find(final Realm realm, final Class<E> objectClass,
                                          final String column, final Date value) {
        return realm.where(objectClass).equalTo(column, value).findFirst();
    }

    public <E extends RealmObject> E find(final Realm realm, final Class<E> objectClass,
                                          final String column, final String value) {
        return realm.where(objectClass).equalTo(column, value).findFirst();
    }

    public <E extends RealmObject> E find(final Realm realm, final Class<E> objectClass,
                                          final String column1, final String value1,
                                          final String column2, final String value2) {
        return realm.where(objectClass).equalTo(column1, value1).equalTo(column2, value2).findFirst();
    }

    public <E extends RealmObject> E find(final Realm realm, final Class<E> objectClass,
                                          final String column, final int value) {
        return realm.where(objectClass).equalTo(column, value).findFirst();
    }

    public <E extends RealmObject> E find(final Realm realm, final Class<E> objectClass,
                                          final String column, final long value) {
        return realm.where(objectClass).equalTo(column, value).findFirst();
    }

    public <E extends RealmObject> E findAnd(final Realm realm, final Class<E> objectClass,
                                             final String column1, final long value1,
                                             final String column2, final long value2) {
        return realm.where(objectClass).equalTo(column1, value1)
                .equalTo(column2, value2).findFirst();
    }

    public <E extends RealmObject> List<E> findAll(final Realm realm, final Class<E> objectClass,
                                                   final String columnSearch, final boolean keyWord) {
        return realm.where(objectClass).equalTo(columnSearch, keyWord).findAll();
    }

    public <E extends RealmObject> List<E> findAll(final Realm realm, final Class<E> objectClass,
                                                   final String columnSearch, final String keyWord) {
        return realm.where(objectClass).equalTo(columnSearch, keyWord, Case.INSENSITIVE).findAll();
    }

    public <E extends RealmObject> List<E> findAllSorted(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final String keyWord,
                                                         final String columnSort, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).
                equalTo(columnSearch, keyWord, Case.INSENSITIVE).
                findAllSorted(columnSort, sort);
    }

    public <E extends RealmObject> List<E> findAllSorted(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final String keyWord,
                                                         final String columnSearch2, final boolean keyWord2,
                                                         final String columnSort, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).
                equalTo(columnSearch, keyWord, Case.INSENSITIVE).
                equalTo(columnSearch2, keyWord2).
                findAllSorted(columnSort, sort);
    }

    public <E extends RealmObject> List<E> findAllSorted(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final boolean keyWord,
                                                         final String columnSort, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).
                equalTo(columnSearch, keyWord).
                findAllSorted(columnSort, sort);
    }

    public <E extends RealmObject> List<E> findAllSorted(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final long keyWord,
                                                         final String columnSort, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).
                equalTo(columnSearch, keyWord).
                findAllSorted(columnSort, sort);
    }

    public <E extends RealmObject> List<E> findAllSorted(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final int keyWord,
                                                         final String columnSort, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).
                equalTo(columnSearch, keyWord).
                findAllSorted(columnSort, sort);
    }

    public <E extends RealmObject> List<E> findAllSorted(final Realm realm, final Class<E> objectClass,
                                                         final String columnSearch, final Integer[] keyWord,
                                                         final String columnSort, final boolean isAscending) {
        Sort sort = Sort.ASCENDING;
        if (!isAscending) sort = Sort.DESCENDING;
        return realm.where(objectClass).
                in(columnSearch, keyWord).
                findAllSorted(columnSort, sort);
    }

    public <E extends RealmObject> List<E> findAllGrouped(final Realm realm, final Class<E> objectClass,
                                                          final String columnGroupBy) {
        return realm.where(objectClass).distinct(columnGroupBy);
    }

    public <E extends RealmObject> void add(final E realmModel) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealm(realmModel);
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void addAll(final List<E> realmModelList) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealm(realmModelList);
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void update(final E realmModel) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(realmModel);
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void updateAll(final List<E> realmModelList) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(realmModelList);
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void deleteWhere(final Class<E> objectClass,
                                                    final String columnSearch, final String keyWord) {
        final Realm realm = Realm.getDefaultInstance();
        final E record = realm.where(objectClass).equalTo(columnSearch, keyWord).findFirst();
        try {
            realm.beginTransaction();
            record.deleteFromRealm();
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void deleteWhere(final Class<E> objectClass,
                                                    final String columnSearch, final long keyWord) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.where(objectClass).equalTo(columnSearch, keyWord).findFirst().deleteFromRealm();
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void deleteAllWhere(final Class<E> objectClass,
                                                       final String columnSearch, final long keyWord) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.where(objectClass).equalTo(columnSearch, keyWord).findAll().deleteAllFromRealm();
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public <E extends RealmObject> void deleteAllWhere(final Class<E> objectClass,
                                                       final String columnSearch, final int keyWord) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.where(objectClass).equalTo(columnSearch, keyWord).findAll().deleteAllFromRealm();
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }

    public void deleteAll(Class<? extends RealmObject> objectClass) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(objectClass);
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }



}
