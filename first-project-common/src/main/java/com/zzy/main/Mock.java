package com.zzy.main;

import com.firstproject.common.util.JsonUtil;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Description：废弃，见framework-ssm
 *
 * @author zhouzhongyi1
 * @date 2019/8/3 12:50
 * @ see
 * @since
 */
public class Mock {

    private static final Boolean[] bools = new Boolean[]{true, false};

    private static final char[] words = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final Random r = new Random();

    private static final int MAX_COLLECTION_LENGTH = 5;

    private static final int MAX_STRING_LENGTH = 15;

    private static final List<Class<?>> clazzes = new ArrayList<>();

    public static synchronized <T> T mockObj(Class<T> clazz) {
        if(clazz == null) {
            return null;
        }
        if(clazz == Map.class || clazz == List.class) {
            return null;
        }
        return mock(null, clazz);
    }

    /**
     * clazz不支持Map,不支持List,尽量用YaoResult
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static synchronized <T> T mock(T obj, Class<T> clazz) {
        if (clazz == Character.class || clazz == Character.TYPE) {
            return (T) (Character) words[r.nextInt(words.length)];
        } else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
            return (T) (Boolean) bools[r.nextInt(bools.length)];
        } else if (clazz == Byte.class || clazz == Byte.TYPE) {
            return (T) (Byte) new Byte("0");
        } else if (clazz == Date.class) {
            return (T) (Date) new Date();
        } else if (clazz == Long.class || clazz == Long.TYPE) {
            return (T) (Long) r.nextLong();
        } else if (clazz == Integer.class || clazz == Integer.TYPE) {
            return (T) (Integer) r.nextInt();
        } else if (clazz == Short.class || clazz == Short.TYPE) {
            return (T) (Short) new Integer(r.nextInt(127)).shortValue();
        } else if (clazz == Float.class || clazz == Float.TYPE) {
            return (T) (Float) r.nextFloat();
        } else if (clazz == Double.class || clazz == Double.TYPE) {
            return (T) (Double) r.nextDouble();
        } else if (clazz == String.class) {
            return (T) randString(r.nextInt(MAX_STRING_LENGTH));
        }

        try {
            T instance = clazz.newInstance();

            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);

                //防止无限递归
                if(f.getType() == clazz) {
                    f.set(instance, null);
                    continue;
                }

                if (f.getType() == Byte.TYPE) {
                    f.setByte(instance, new Byte("0"));
                } else if (f.getType() == Byte.class) {
                    f.setByte(instance, new Byte("0"));
                } else if (f.getType() == Character.TYPE) {
                    f.setChar(instance, words[r.nextInt(words.length)]);
                } else if (f.getType() == Character.class) {
                    f.set(instance, words[r.nextInt(words.length)]);
                } else if (f.getType() == Boolean.TYPE) {
                    f.setBoolean(instance, bools[r.nextInt(bools.length)]);
                } else if (f.getType() == Boolean.class) {
                    f.set(instance, bools[r.nextInt(bools.length)]);
                } else if (f.getType() == Long.TYPE) {
                    f.setLong(instance, r.nextLong());
                } else if (f.getType() == Long.class) {
                    f.set(instance, r.nextLong());
                } else if (f.getType() == Integer.TYPE) {
                    f.setInt(instance, r.nextInt());
                } else if (f.getType() == Integer.class) {
                    f.set(instance, r.nextInt());
                } else if (f.getType() == Short.TYPE) {
                    f.setShort(instance, new Integer(r.nextInt(127)).shortValue());
                } else if (f.getType() == Short.class) {
                    f.set(instance, new Integer(r.nextInt(127)).shortValue());
                } else if (f.getType() == Float.TYPE) {
                    f.setFloat(instance, r.nextFloat());
                } else if (f.getType() == Float.class) {
                    f.set(instance, r.nextFloat());
                } else if (f.getType() == Double.TYPE) {
                    f.setDouble(instance, r.nextDouble());
                } else if (f.getType() == Double.class) {
                    f.set(instance, r.nextDouble());
                } else if (f.getType() == String.class) {
                    f.set(instance, randString(r.nextInt(MAX_STRING_LENGTH)));
                } else if (f.getType() == List.class) {
                    int size = r.nextInt(MAX_COLLECTION_LENGTH);
                    List<Object> list = new ArrayList<>(size);
                    ParameterizedType pt = null;
                    for (int i = 0; i < size; i++) {
                        pt = (ParameterizedType) f.getGenericType();
                        list.add(mock(null, (Class) pt.getActualTypeArguments()[0]));
                    }
                    f.set(instance, list);
                } else if (f.getType() == Map.class) {
                    int size = r.nextInt(MAX_COLLECTION_LENGTH);
                    Map<Object, Object> map = new HashMap<>();
                    ParameterizedType pt = null;
                    for (int i = 0; i < size; i++) {
                        pt = (ParameterizedType) f.getGenericType();
                        map.put(mock(null, (Class) pt.getActualTypeArguments()[0]),
                                mock(null, (Class) pt.getActualTypeArguments()[1]));
                    }
                    f.set(instance, map);
                } else if (f.getType() == Date.class) {
                    f.set(instance, new Date());
                } else {
                    f.set(instance, mock(null, f.getType()));
                }
            }

            return instance;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String randString(int count) {
        if (count <= 0) {
            count = 1;
        }

        int length = words.length;
        char[] text = new char[count];
        for (int i = 0; i < count; i++) {
            text[i] = words[r.nextInt(length)];
        }

        return new String(text);
    }

    public static void main(String[] args) {
        YaoResult yaoResult = mock(null, YaoResult.class);
        System.out.println(JsonUtil.toJSONString(yaoResult));
    }
}

@Data
class YaoResult {
    private int code;
    private String msg;
    private Map<Long, String> data;
    private boolean success;
}
