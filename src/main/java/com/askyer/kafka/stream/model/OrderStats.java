package com.askyer.kafka.stream.model;

import java.util.*;

/**
 * Created by askyer on 2018/5/27.
 */
public class OrderStats {
    private String desc;
    private int count;

    public int getCount() {
        return count;
    }
    private static Map<String, OrderUserItem> map = new HashMap<String, OrderUserItem>();
    public void clear() {
        map.clear();
    }
    public OrderStats add(OrderUserItem item) {
        String key = item.getCategory() + item.getItem_name();
        if (map.containsKey(key)){
            //merge result
            OrderUserItem old = map.get(key);
            map.put(key, OrderUserItem.add2OrderUserItem(old, item));
        } else {
            map.put(key, item);
        }
        return this;
    }

    public OrderStats compute() {
        this.count = this.map.size();
        List<Map.Entry<String, OrderUserItem>> list = new ArrayList<>();

        for(Map.Entry<String, OrderUserItem> entry : map.entrySet()){
            list.add(entry);
        }

        list.sort(new Comparator<Map.Entry<String, OrderUserItem>>(){
            @Override
            public int compare(Map.Entry<String, OrderUserItem> o1, Map.Entry<String, OrderUserItem> o2) {
                double d2 =  o2.getValue().getAmount();
                double d1 = o1.getValue().getAmount();
                if ((d1 - d2) >0 ){
                    return  1;
                } else if (d1 - d2 < 0 ) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, OrderUserItem> entry: list){
            OrderUserItem v = entry.getValue();
            sb.append("\ncat:");
            sb.append(v.getCategory());
            sb.append(",name:");
            sb.append(v.getItem_name());
            sb.append(",quantity:");
            sb.append(v.getQuantity());
            sb.append(",price:");
            sb.append(v.getPrice());
            sb.append(",amount:");
            sb.append(v.getAmount());
        }

        this.desc =sb.toString();
        System.out.println(this.desc );
        return this;
    }


}
