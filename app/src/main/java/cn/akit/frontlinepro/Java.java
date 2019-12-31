package cn.akit.frontlinepro;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import kotlin.jvm.internal.Intrinsics;

/**
 * Created by chenYuXuan on 2019/8/1.
 * email : southxvii@163.com
 */
public class Java {

    public static void threadTest() {
        ExecutorService service = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        for (int i = 0; i < 500; i++) {
            final int position = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.print("Go" + position + "\n");
                }
            });
        }

        System.out.print("Over\n");
        service.shutdown();
        System.out.print("IsShutDown " + service.isShutdown());
        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.print("Go After Over" + "\n");
            }
        });
    }


    public static void parse(String xml) {
        try {
            XmlPullParser parser = getXmlPullParser(xml);
            assert (parser.getName().equals("message"));
            final int initialDepth = parser.getDepth();
            System.out.print("Depth " + initialDepth);
            outerloop:
            while (true) {
                int eventType = parser.next();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        System.out.print("StartTag " + parser.getName());
                        String elementName = parser.getName();
                        String namespace = parser.getNamespace();
                        switch (elementName) {
                            default:
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        System.out.print("EndTag " + parser.getName() + " Depth " + parser.getDepth());
                        if (parser.getDepth() == initialDepth) {
                            break outerloop;
                        }
                        break;
                }
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static XmlPullParser getXmlPullParser(String xml) throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser pullParser = factory.newPullParser();
        Reader reader = new StringReader(xml);
        pullParser.setInput(reader);
        return pullParser;
    }

    public static void main(String[] args) {
        ArrayList<Interval> list = new ArrayList<>();
        list.add(new Interval(1, 5));
        list.add(new Interval(7, 9));
        list.add(new Interval(14, 25));
        list.add(new Interval(30, 90));


        list.add(new Interval(90, 92));


        list = mergeInterval(list);
        for (Interval o : list) {
            System.out.print("Interval " + o);
        }

        System.out.print("\n");




//        list = removeInterval(list, new Interval(33, 90));
//        for (Interval o : list) {
//            System.out.print("Interval " + o);
//        }
//
//        System.out.print("\n");
//
//        list = removeInterval(list, new Interval(17, 20));
//        for (Interval o : list) {
//            System.out.print("Interval " + o);
//        }


    }


    private static String formateStr(String str) {

        return new String().format("%02d", str);
    }

    public static final class Interval {
        private final long start;
        private final long end;

        public final long getStart() {
            return this.start;
        }

        public final long getEnd() {
            return this.end;
        }

        public Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }





    private static final ArrayList<Interval> removeInterval(ArrayList list, Interval targetInterval) {
        if (list.isEmpty()) {
            return list;
        } else {
            long min = targetInterval.getStart();
            long max = targetInterval.getEnd();
            ArrayList listResult = new ArrayList();
            int i = 0;

            for(int var9 = list.size(); i < var9; ++i) {
                Object var10000 = list.get(i);
                Intrinsics.checkExpressionValueIsNotNull(var10000, "list[i]");
                Interval current = (Interval)var10000;
                long var11 = current.getStart() + 1L;
                if (min <= var11) {
                    if (max >= var11) {
                        var11 = current.getEnd();
                        if (min <= var11) {
                            if (max >= var11) {
                                continue;
                            }
                        }

                        listResult.add(new Interval(max, current.getEnd()));
                        continue;
                    }
                }

                if (current.getStart() + 1L < min) {
                    var11 = current.getEnd();
                    if (min <= var11) {
                        if (max >= var11) {
                            listResult.add(new Interval(current.getStart(), min - 1L));
                            continue;
                        }
                    }

                    if (current.getEnd() < min) {
                        listResult.add(new Interval(current.getStart(), current.getEnd()));
                    } else {
                        listResult.add(new Interval(current.getStart(), min - 1L));
                        listResult.add(new Interval(max, current.getEnd()));
                    }
                } else {
                    listResult.add(new Interval(current.getStart(), current.getEnd()));
                }
            }

            return listResult;
        }
    }





    public static ArrayList<Interval> mergeInterval(@NotNull ArrayList list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        if (list.isEmpty()) {
            return  list;
        } else {
            int i = 0;

            for (int var3 = list.size(); i < var3; ++i) {
                int j = i + 1;

                for (int var5 = list.size(); j < var5; ++j) {
                    if (((Interval) list.get(i)).getStart() > ((Interval) list.get(j)).getStart()) {
                        Object var10000 = list.get(j);
                        Intrinsics.checkExpressionValueIsNotNull(var10000, "list[j]");
                        Interval temp = (Interval) var10000;
                        list.set(j, list.get(i));
                        list.set(i, temp);
                    }
                }
            }

            ArrayList listResult = new ArrayList();
            long start = ((Interval) list.get(0)).getStart();
            long end = ((Interval) list.get(0)).getEnd();
            int ii = 1;

            for (int var8 = list.size(); ii < var8; ++ii) {
                if (end >= ((Interval) list.get(ii)).getStart()) {
                    end = Math.max(end, ((Interval) list.get(ii)).getEnd());
                } else {
                    listResult.add(new Interval(start, end));
                    start = ((Interval) list.get(ii)).getStart();
                    end = ((Interval) list.get(ii)).getEnd();
                }
            }

            listResult.add(new Interval(start, end));
            return listResult;
        }
    }
}
