package com.saras.archetype.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ToString {
    public static final String ALL_ASTERISK = "******";
    private static final String packageName = getPackageName(ToString.class);
    private static final String NULL = "null";
    private static final Map<String, I> cache = Maps.newConcurrentMap();
    private static final char SEPARATOR_CHAR_ASTERISK = '*';
    private static final String DATE_FORMART = "yyyy-MM-dd HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(ToString.class);
    public static boolean logSource = false;
    public static String dumpClass = null;

    public ToString() {
    }

    public static String toString(Object o) {
        if (o == null) {
            return "null";
        } else {
            I i = cache.get(o.getClass().getName());
            if (i == null) {
                synchronized (o.getClass()) {
                    i = cache.get(o.getClass().getName());
                    if (i == null) {
                        Generator generator = new Generator(o.getClass());
                        try {
                            i = generator.generate().newInstance();
                            cache.put(o.getClass().getName(), i);
                        } catch (IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return i != null ? i.toString(o) : null;
        }
    }

    private static String getPackageName(Class<?> clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(".");
        return lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "";
    }

    public static String toString(Collection collection, int size) {
        if (collection == null) {
            return "null";
        } else {
            Iterator it = collection.iterator();
            if (!it.hasNext()) {
                return "[]";
            } else {
                StringBuilder sb = new StringBuilder(10 * Math.min(size, collection.size()));
                sb.append('[');

                for (int i = 0; i < size; ++i) {
                    Object e = it.next();
                    sb.append(e == collection ? "(this Collection)" : e);
                    if (!it.hasNext()) {
                        return sb.append(']').toString();
                    }

                    sb.append(',');
                }

                sb.append("...]");
                return sb.toString();
            }
        }
    }

    public static String toString(Map map, int size, List<String> maskKeys) {
        if (map == null) {
            return "null";
        } else {
            Iterator it = map.entrySet().iterator();
            if (!it.hasNext()) {
                return "{}";
            } else {
                StringBuilder sb = new StringBuilder(10 * Math.min(size, map.size()));
                sb.append('{');

                for (int i = 0; i < size; ++i) {
                    Map.Entry e = (Map.Entry) it.next();
                    Object key = e.getKey();
                    Object value = e.getValue();
                    sb.append(key == map ? "(this Map)" : key);
                    sb.append('=');
                    if (maskKeys != null && maskKeys.contains(key)) {
                        String maskValue;
                        if (value == null) {
                            maskValue = "null";
                        } else if (value instanceof ToString.Masked) {
                            maskValue = value.toString();
                        } else {
                            maskValue = mask(value.toString());
                        }

                        sb.append(value == map ? "(this Map)" : maskValue);
                    } else {
                        sb.append(value == map ? "(this Map)" : value);
                    }

                    if (!it.hasNext()) {
                        return sb.append('}').toString();
                    }

                    sb.append(',');
                }

                sb.append("...}");
                return sb.toString();
            }
        }
    }

    public static String toString(String str, int size) {
        return str == null ? "null" : (str.length() < size ? str : str.substring(0, size) + "...");
    }

    public static String toString(long[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(5 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(int[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(5 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(short[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(3 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(char[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(1 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(byte[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(1 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(boolean[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(5 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(float[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(5 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(double[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(5 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(a[i]);
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(Object[] a, int size) {
        if (a == null) {
            return "null";
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "[]";
            } else {
                StringBuilder b = new StringBuilder(10 * Math.min(size, a.length));
                b.append('[');

                for (int i = 0; i < size; ++i) {
                    b.append(String.valueOf(a[i]));
                    if (i == iMax) {
                        return b.append(']').toString();
                    }

                    b.append(",");
                }

                b.append("...]");
                return b.toString();
            }
        }
    }

    public static String toString(byte i) {
        return Byte.toString(i);
    }

    public static String toString(byte[] i) {
        return toString((byte[]) i, 500);
    }

    public static String toString(short i) {
        return Short.toString(i);
    }

    public static String toString(short[] i) {
        return toString((short[]) i, 500);
    }

    public static String toString(int i) {
        return Integer.toString(i);
    }

    public static String toString(int[] i) {
        return toString((int[]) i, 500);
    }

    public static String toString(long i) {
        return Long.toString(i);
    }

    public static String toString(long[] i) {
        return toString((long[]) i, 500);
    }

    public static String toString(char i) {
        return Character.toString(i);
    }

    public static String toString(char[] i) {
        return toString((char[]) i, 500);
    }

    public static String toString(float i) {
        return Float.toString(i);
    }

    public static String toString(float[] i) {
        return toString((float[]) i, 500);
    }

    public static String toString(double i) {
        return Double.toString(i);
    }

    public static String toString(double[] i) {
        return toString((double[]) i, 500);
    }

    public static String toString(boolean i) {
        return Boolean.toString(i);
    }

    public static String toString(boolean[] i) {
        return toString((boolean[]) i, 500);
    }

    public static String toString(String i) {
        return toString((String) i, 1000);
    }

    public static String toString(String[] i) {
        return toString((Object[]) i, 500);
    }

    public static String toString(Collection i) {
        return toString((Collection) i, 500);
    }

    public static String toString(Map i) {
        return toString(i, 500, (List) null);
    }

    public static String toString(Object[] i) {
        return toString((Object[]) i, 500);
    }

    public static String mask(String str) {
        if (str == null) {
            return "null";
        } else {
            int len = str.length();
            if (len == 0) {
                return str;
            } else if (len == 1) {
                return String.valueOf('*');
            } else {
                int maskLen;
                int begin;
                if (len >= 16 && len <= 22) {
                    maskLen = len - 6 - 4;
                    begin = 6;
                } else {
                    if (len > 500) {
                        str = str.substring(0, 500) + "...";
                    }

                    len = str.length();
                    maskLen = Math.max(len / 2, 1);
                    begin = (len - maskLen) / 2;
                }

                char[] chars = str.toCharArray();
                char[] mask = repeatAsterisk(maskLen);
                System.arraycopy(mask, 0, chars, begin, maskLen);
                return new String(chars);
            }
        }
    }

    public static String maskAll(String str) {
        return str != null && str.length() != 0 ? "******" : str;
    }

    public static <E> List<E> newList(E... e) {
        ArrayList list = new ArrayList();
        if (e != null) {
            Object[] var2 = e;
            int var3 = e.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object e1 = var2[var4];
                list.add(e1);
            }
        }

        return list;
    }

    public static String parseDate(Date date) {
        return date == null ? "null" : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    private static char[] repeatAsterisk(int len) {
        char[] chars = new char[len];
        Arrays.fill(chars, '*');
        return chars;
    }

    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface ToStringMethod {
        String value();
    }

    public interface Masked extends Serializable {
        Object getOri();

        boolean masked();
    }

    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Maskable {
        boolean maskAll() default false;

        String[] maskKeys() default {};
    }

    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Invisible {
    }

    public interface I {
        String toString(Object var1);
    }

    public static class Generator {
        public static final int TO_STRING_ARRAY_SIZE_THRESHOLD = 500;
        public static final int TO_STRING_COLLECTION_SIZE_THRESHOLD = 500;
        public static final int TO_STRING_MAP_SIZE_THRESHOLD = 500;
        public static final int TO_STRING_STRING_SIZE_THRESHOLD = 100;
        public static final int TO_STRING_MASK_SIZE_THRESHOLD = 500;
        private static final String SOURCE = "s";
        private static AtomicInteger index = new AtomicInteger(100000);
        private Class source;
        private String beginSource;
        private String endSources;
        private List<String> bodySources = Lists.newArrayList();
        private int fieldIndex = 0;
        private int fieldCount = 0;
        private Map<String, Field> fieldMap = Maps.newHashMap();
        private List<String> maskKeyFields = Lists.newArrayList();

        public Generator(Class source) {
            this.source = source;
            this.parseFields();
        }

        private void parseFields() {
            for (Class acls = this.source; acls != Object.class; acls = acls.getSuperclass()) {
                Field[] fields = acls.getDeclaredFields();
                Field[] var3 = fields;
                int var4 = fields.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    Field field = var3[var5];
                    if (!this.fieldMap.containsKey(field.getName())) {
                        this.fieldMap.put(field.getName(), field);
                    }
                }
            }

        }

        private void generateBegin() {
            this.beginSource = "public String toString(Object xxoo){\n";
            String convertSource = String.format("%s %s=(%s)xxoo;", new Object[]{this.source.getName(), "s", this.source.getName(), "s"});
            this.beginSource = this.beginSource + convertSource;
        }

        private void generateEnd() {
            this.endSources = "\nsb.append(\"}\");\n return sb.toString();}";
        }

        private void generateBody() {
            List pds = this.getPropertyDescriptors();
            this.bodySources.add(String.format("if(%s==null){return null;}\n", new Object[]{"s"}));
            this.bodySources.add(String.format("StringBuilder sb=new StringBuilder(%d);\n", new Object[]{Integer.valueOf(pds.size() * 20)}));
            this.bodySources.add(String.format("sb.append(\"%s{\");", new Object[]{this.source.getSimpleName()}));
            this.fieldCount = pds.size();
            String toStringClassName = ToString.class.getName();
            Iterator var3 = pds.iterator();

            while (true) {
                while (true) {
                    while (var3.hasNext()) {
                        PropertyDescriptor pd = (PropertyDescriptor) var3.next();
                        Method read = pd.getReadMethod();
                        if (read != null && this.getAnnotation(pd, ToString.Invisible.class) == null) {
                            ++this.fieldIndex;
                            Class returnType = pd.getReadMethod().getReturnType();
                            Class proptertyType = pd.getPropertyType();
                            String propName = pd.getName();
                            String readMethod = this.buildReadMethod(pd);
                            if (returnType.isArray()) {
                                this.buildArray(toStringClassName, readMethod, propName);
                            } else if (proptertyType != String.class && returnType != String.class) {
                                if (!this.isCollection(proptertyType) && !this.isCollection(returnType)) {
                                    if (!this.isMap(proptertyType) && !this.isMap(returnType)) {
                                        if (proptertyType != Date.class && returnType != Date.class) {
                                            this.doProp(propName, readMethod);
                                        } else {
                                            this.buildDate(toStringClassName, readMethod, propName);
                                        }
                                    } else {
                                        this.buildMap(toStringClassName, pd, readMethod, propName);
                                    }
                                } else {
                                    this.buildCollection(toStringClassName, readMethod, propName);
                                }
                            } else {
                                this.buildString(toStringClassName, pd, readMethod, propName);
                            }
                        } else {
                            --this.fieldCount;
                        }
                    }

                    return;
                }
            }
        }

        private String buildReadMethod(PropertyDescriptor pd) {
            String readMethod = String.format("%s.%s()", new Object[]{"s", pd.getReadMethod().getName()});
            ToString.ToStringMethod toStringMethod = (ToString.ToStringMethod) this.getAnnotation(pd, ToString.ToStringMethod.class);
            if (toStringMethod != null) {
                readMethod = String.format("%s==null?null:%s.%s()", new Object[]{readMethod, readMethod, toStringMethod.value()});
            }

            return readMethod;
        }

        private void buildArray(String toStringClassName, String readMethod, String propName) {
            readMethod = String.format("%s.toString(%s,%d)", new Object[]{toStringClassName, readMethod, Integer.valueOf(500)});
            this.doProp(propName, readMethod);
        }

        private void buildDate(String toStringClassName, String readMethod, String propName) {
            readMethod = String.format("%s.parseDate((java.util.Date)%s)", new Object[]{toStringClassName, readMethod});
            this.doProp(propName, readMethod);
        }

        private void buildCollection(String toStringClassName, String readMethod, String propName) {
            readMethod = String.format("%s.toString((java.util.Collection)%s,%d)", new Object[]{toStringClassName, readMethod, Integer.valueOf(500)});
            this.doProp(propName, readMethod);
        }

        private void buildMap(String toStringClassName, PropertyDescriptor pd, String readMethod, String propName) {
            ToString.Maskable maskable = (ToString.Maskable) this.getAnnotation(pd, ToString.Maskable.class);
            if (maskable == null) {
                readMethod = String.format("%s.toString((java.util.Map)%s,%d,null)", new Object[]{toStringClassName, readMethod, Integer.valueOf(500)});
            } else {
                List maskKeyList = maskable.maskKeys() != null && maskable.maskKeys().length != 0 ? Arrays.asList(maskable.maskKeys()) : null;
                readMethod = String.format("%s.toString((java.util.Map)%s,%d,%s)", new Object[]{toStringClassName, readMethod, Integer.valueOf(500), this.maskKeyField(maskKeyList)});
            }

            this.doProp(propName, readMethod);
        }

        private void buildString(String toStringClassName, PropertyDescriptor pd, String readMethod, String propName) {
            ToString.Maskable maskable = (ToString.Maskable) this.getAnnotation(pd, ToString.Maskable.class);
            if (maskable == null) {
                readMethod = String.format("%s.toString((String)%s,%d)", new Object[]{toStringClassName, readMethod, Integer.valueOf(500)});
            } else if (maskable.maskAll()) {
                readMethod = String.format("%s.maskAll((String)%s)", new Object[]{toStringClassName, readMethod});
            } else {
                readMethod = String.format("%s.mask((String)%s)", new Object[]{toStringClassName, readMethod});
            }

            this.doProp(propName, readMethod);
        }

        private <T extends Annotation> T getAnnotation(PropertyDescriptor pd, Class<T> annotationType) {
            Annotation t = pd.getReadMethod().getAnnotation(annotationType);
            if (t != null) {
                return (T) t;
            } else {
                Field field = (Field) this.fieldMap.get(pd.getName());
                if (field != null) {
                    t = field.getAnnotation(annotationType);
                }

                return (T) t;
            }
        }

        private void doProp(String name, String readMethod) {
            this.bodySources.add("\nsb.append(\"" + name + "=\");");
            this.bodySources.add("\nsb.append(" + readMethod + ");");
            if (this.fieldIndex < this.fieldCount) {
                this.bodySources.add("\nsb.append(\",\");");
            }

        }

        public Class<I> generate() {
            this.generateBegin();
            this.generateBody();
            this.generateEnd();
            StringBuilder sb = new StringBuilder();
            sb.append(this.beginSource);
            Iterator source = this.bodySources.iterator();

            while (source.hasNext()) {
                String pool = (String) source.next();
                sb.append(pool);
            }

            sb.append(this.endSources);
            String source1 = sb.toString();
            if (ToString.logSource) {
                ToString.logger.info("\n{}", source1);
            }

            ClassPool pool1 = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool1.insertClassPath(classPath);
            CtClass cc = pool1.makeClass(ToString.packageName + ".ToStringImpl" + index.incrementAndGet());
            Class copyClass = null;

            try {
                cc.addInterface(pool1.get(ToString.I.class.getName()));
                Iterator e = this.maskKeyFields.iterator();

                while (e.hasNext()) {
                    String maskKeyField = (String) e.next();
                    cc.addField(CtField.make(maskKeyField, cc));
                }

                cc.addMethod(CtNewMethod.make(source1, cc));
                if (ToString.dumpClass != null) {
                    CtClass.debugDump = ToString.dumpClass;
                }

                ClassLoader e1 = this.getDefaultClassLoader();
                ToString.logger.debug("classloader:{}", e1);
                copyClass = cc.toClass(e1, (ProtectionDomain) null);
                return copyClass;
            } catch (Exception var9) {
                throw new RuntimeException(var9);
            }
        }

        private String maskKeyField(List<String> params) {
            StringBuilder sb = new StringBuilder();
            String fieldName = "maskKeys" + index.incrementAndGet();
            sb.append("private java.util.List ").append(fieldName).append("=com.dph.core.util.ToString.newList(");
            if (params != null && params.size() != 0) {
                sb.append("new String[]{");
                Iterator var4 = params.iterator();

                while (var4.hasNext()) {
                    String s = (String) var4.next();
                    sb.append("\"" + s + "\",");
                }

                sb.deleteCharAt(sb.length() - 1);
                sb.append("}");
            } else {
                sb.append("null");
            }

            sb.append(");");
            this.maskKeyFields.add(sb.toString());
            return fieldName;
        }

        private ClassLoader getDefaultClassLoader() {
            ClassLoader cl = null;

            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Exception var3) {
            }

            if (cl == null) {
                cl = this.getClass().getClassLoader();
            }

            return cl;
        }

        public List<PropertyDescriptor> getPropertyDescriptors() {
            Class clazz = this.source;

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                PropertyDescriptor[] e = beanInfo.getPropertyDescriptors();
                ArrayList propertyDescriptorList = new ArrayList();
                PropertyDescriptor[] var5 = e;
                int var6 = e.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    PropertyDescriptor propertyDescriptor = var5[var7];
                    String name = propertyDescriptor.getName();
                    if (this.fieldMap.containsKey(name)) {
                        propertyDescriptorList.add(propertyDescriptor);
                    }
                }

                return propertyDescriptorList;
            } catch (IntrospectionException var10) {
                throw new RuntimeException(var10);
            }
        }

        private boolean isMap(Class<?> type) {
            return Map.class.isAssignableFrom(type);
        }

        private boolean isCollection(Class<?> type) {
            return Collection.class.isAssignableFrom(type);
        }
    }

    public static class MaskedString extends ToString.AbstractMasked {
        private String ori;

        public MaskedString(String ori, boolean mask) {
            super(mask);
            this.ori = ori;
        }

        @Override
        public String getOri() {
            return this.ori;
        }
    }

    public static class MaskedObject extends ToString.AbstractMasked {
        private Object ori;

        public MaskedObject(Object ori, boolean mask) {
            super(mask);
            this.ori = ori;
        }

        @Override
        public Object getOri() {
            return this.ori;
        }
    }

    public abstract static class AbstractMasked implements ToString.Masked {
        private boolean mask = false;

        public AbstractMasked(boolean mask) {
            this.mask = mask;
        }

        @Override
        public String toString() {
            return this.getOri() == null ? "null" : (this.masked() ? ToString.mask(this.getOri().toString()) : this.getOri().toString());
        }

        @Override
        public boolean masked() {
            return this.mask;
        }
    }
}
