package concurrent.chapter4;

/**
 * 带有时间戳的对象引用
 *
 * @author tangj
 * @date 2018/1/30 23:08
 * AtomicReference无法解决对象反复修改，同步失败的问题，是因为对象在修改过程中，丢失了对象信息。
 * 对象值本身与状态被画上了等号。因此，我们只要能够记录对象在修改过程中的对象值，就可以很好的解决对象
 * 被反复修改导致线程无法正确判断对象状态的问题。
 *
 * AtomicStampedReference内部不仅维护了对象值，而且还维护了一个时间戳。
 * 它在更新数据时，还需要更新时间戳。
 * 当对象在修改时，对象值以及时间戳都必须满足期望值写入才会成功。
 */
public class AtomicStampedReferenceDemo {
}
