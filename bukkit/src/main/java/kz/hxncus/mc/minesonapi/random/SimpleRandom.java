package kz.hxncus.mc.minesonapi.random;

import it.unimi.dsi.util.XoRoShiRo128PlusRandom;

public class SimpleRandom extends XoRoShiRo128PlusRandom {
    private static SimpleRandom instance;

    protected SimpleRandom() {
    }

    @Override
    public int nextInt(int origin, int bound) {
        return super.nextInt(bound - origin) + origin;
    }

    public static SimpleRandom get() {
        // Техника, которую мы здесь применяем, называется «блокировка с двойной
        // проверкой» (Double-Checked Locking). Она применяется, чтобы
        // предотвратить создание нескольких объектов-одиночек, если метод будет
        // вызван из нескольких потоков одновременно.
        //
        // Хотя переменная `result` вполне оправданно кажется здесь лишней, она
        // помогает избежать подводных камней реализации DCL в Java.
        //
        // Больше об этой проблеме можно почитать здесь:
        // https://ru.wikipedia.org/wiki/%D0%91%D0%BB%D0%BE%D0%BA%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0_%D1%81_%D0%B4%D0%B2%D0%BE%D0%B9%D0%BD%D0%BE%D0%B9_%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%BE%D0%B9
        SimpleRandom result = instance;
        if (result != null) {
            return result;
        }
        synchronized(SimpleRandom.class) {
            if (instance == null) {
                instance = new SimpleRandom();
            }
            return instance;
        }
    }
}
