package com.kakopay.homework.payment.external.linkdata.stringdata;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;


@Slf4j
public class StringDataGenerator {

    public static String getStringData(Object obj) {

        if (Objects.isNull(obj)) {
            return "";
        }

        int stringLength = 0;
        StringBuilder sb = new StringBuilder();

        for (Field field : obj.getClass().getDeclaredFields()) {

            FixedLengthField fixedLengthField = field.getDeclaredAnnotation(FixedLengthField.class);

            if (ObjectUtils.isEmpty(fixedLengthField))
                continue;

            field = setAccessible(field);
            String fixedLengthFieldString = fixedLengthFieldsToString(obj, field, fixedLengthField);

            log.debug("fixed length Field String, field name {}, align {} : {}",
                    field.getName(),
                    fixedLengthField.fieldSet().getAlignment(),
                    fixedLengthFieldString);

            sb.append(fixedLengthFieldString);
            stringLength += fixedLengthFieldString.length();
        }

        checkLength(obj, stringLength);

        return sb.toString();
    }

    private static void checkLength(Object obj, int sumFieldStringLength) {

        FixedLengthType fixedLengthType = obj.getClass().getDeclaredAnnotation(FixedLengthType.class);

        log.debug("fixedLengthType.length() : {}", fixedLengthType.length());
        log.debug("sumFieldStringLength : {}", sumFieldStringLength);

        if (ObjectUtils.isNotEmpty(fixedLengthType) && fixedLengthType.length() > 0) {
            assert (fixedLengthType.length() == sumFieldStringLength);
        }
    }


    private static String fixedLengthFieldsToString(Object obj, Field field, FixedLengthField fixedLengthField) {
        try {

            Object value = field.get(obj);
            FieldSet fieldSet = fixedLengthField.fieldSet();

            switch (fieldSet.getAlignment()) {
                case LEFT:
                    return StringUtils.rightPad(String.valueOf(value), fixedLengthField.length(), fieldSet.getPadChar());
                default:
                    return StringUtils.leftPad(String.valueOf(value), fixedLengthField.length(), fieldSet.getPadChar());
            }

        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    private static Field setAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        return field;
    }

}
