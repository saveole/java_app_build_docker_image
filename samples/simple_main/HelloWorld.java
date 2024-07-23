import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

class HelloWorld {
    void main() {
        System.out.println("Hello world!");

        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println(String.format("Runtime.availableProcessors: %d", Runtime.getRuntime().availableProcessors()));
        System.out.println(String.format("OperatingSystemMXBean.getAvailableProcessors: %d", osBean.getAvailableProcessors()));
        System.out.println(String.format("OperatingSystemMXBean.getTotalMemorySize: %d", osBean.getTotalMemorySize()));
        System.out.println(String.format("OperatingSystemMXBean.getFreeMemorySize: %d", osBean.getFreeMemorySize()));
        System.out.println(String.format("OperatingSystemMXBean.getTotalSwapSpaceSize: %d", osBean.getTotalSwapSpaceSize()));
        System.out.println(String.format("OperatingSystemMXBean.getFreeSwapSpaceSize: %d", osBean.getFreeSwapSpaceSize()));
        System.out.println(String.format("OperatingSystemMXBean.getCpuLoad: %f", osBean.getCpuLoad()));
    }
}
