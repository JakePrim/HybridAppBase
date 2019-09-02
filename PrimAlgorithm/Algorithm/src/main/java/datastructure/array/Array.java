package datastructure.array;

/**
 * 数组结构
 */
public class Array<T> {
    private T[] data;
    private int size;

    public Array(int capacity) {
        data = (T[]) new Object[capacity];
        size = 0;
    }

    public Array() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //O(1)
    public void addLast(T e) {
        add(size, e);
    }

    //O(n)
    public void addFirst(T e) {
        add(0, e);
    }

    //添加元素 O(n)
    public void add(int index, T e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("add error request index < 0 || index > capacity");
        }

        if (size == data.length) {
            //数组空间的扩容 为原来的两倍
            resize(2 * data.length);
        }

        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    //动态数组 使数组的容量可伸缩的，开创新的数组 将旧的数组全部放到新数组中。改变旧数组的指向新的空间
    private void resize(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    //O(n)
    public T removeFirst() {
        return remove(0);
    }

    //O(1)
    public T removeLast() {
        return remove(size - 1);
    }

    //数组中存在一个元素e 只删除一个
    public boolean removeElement(T e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    //删除所有重复的元素
    public boolean removeElementAll(T e) {
        return removeAll(e);
    }

    private boolean removeAll(T e) {
        int index = find(e);
        if (index == -1) {
            return true;
        }
        remove(index);
        removeAll(e);
        return false;
    }

    //删除某个索引
    public synchronized T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("add error request index < 0 || index > size");
        }
        T e = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null;
        //如果当前的元素个数已经小到了一个程度 1/2 的程度则缩小容量
        //为了防止出现震荡的情况 如：位于扩容与缩容临界点 不断的添加或者删除 就会不断的扩容和缩容，时间复杂度都是O(n)
        //所以将元素的个数达到总容量的1/4时才进行缩容 到总容量的一半 可以有效的防止上述的临界点的问题
        if (size == data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return e;
    }


    //获取某个元素
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("add error request index < 0 || index > size");
        }
        return data[index];
    }

    //更新某个元素
    public void set(int index, T e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("add error request index < 0 || index > size");
        }
        data[index] = e;
    }

    //是否存在某个元素
    public boolean contains(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    //找到元素的索引
    public int find(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    //找到所有重复的元素
    public int[] findAll(T e) {
        int[] indexs = new int[size];
        int k = 0;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                indexs[k] = i;
                k++;
            } else {
                indexs[k] = -1;
                k++;
            }
        }
        return indexs;
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();
        res.append(String.format("Array:size:%d,capacity:%d\n", size, data.length));
        res.append("[");
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(",");
            }
        }
        res.append("]");
        return res.toString();
    }
}
