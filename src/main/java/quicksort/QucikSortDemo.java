package quicksort;

/**
 * @author tangj
 * @date 2018/3/20 22:44
 */
public class QucikSortDemo {
    public static void main(String arg[]) {

        int[] numbers = {10, 89, 87, 76, 56, 46, 11, 75, 32, 35, 98};
        System.out.println("排序前：");
        printArr(numbers);

        quickSort(numbers);
        System.out.println("排序后：");
        printArr(numbers);
    }

    /**
     * 查找出中轴位置(默认是最低为low)的在number数组排序后所在位置
     *
     * @param numbers
     * @param low
     * @param high
     * @return
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        // 数组的第一位作为中轴
        int temp = numbers[low];
        while (low < high) {
            while (low < high && numbers[high] > temp) {
                high--;
            }
            // 比中轴晓得记录移动到低端
            numbers[low] = numbers[high];
            while (low < high && numbers[low] < temp) {
                low++;
            }
            // 比中轴大的记录移到高端
            numbers[high] = numbers[low];

        }
        // 中轴记录到尾
        numbers[low] = temp;
        // 返回中轴的位置
        return low;
    }

    /**
     * 分治排序
     *
     * @param numbers
     * @param low
     * @param high
     */
    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            // 将numbers数组进行一分为二
            int middle = getMiddle(numbers, low, high);
            // 将低字段表进行递归排序
            quickSort(numbers, low, middle - 1);
            // 将高字段表进行递归排序
            quickSort(numbers, middle + 1, high);
        }
    }

    public static void quickSort(int[] numbers) {
        if (numbers.length > 0) {
            quickSort(numbers, 0, numbers.length - 1);
        }
    }

    public static void printArr(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + ",");
        }
        System.out.println("");
    }

}
