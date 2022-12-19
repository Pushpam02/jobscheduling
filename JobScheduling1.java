import java.util.Comparator;
import java.util.Arrays;
class JobScheduling1{

    static class Job {
        int start, finish, profit;
        public Job(int start, int finish, int profit) {
            this.start = start;
            this.finish = finish;
            this.profit = profit;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for(int i=0;i<n;i++) {
            jobs[i] = new Job(startTime[i],endTime[i],profit[i]);
        }
        return scheduleApt(jobs);
    }

    private int scheduleApt(Job[] jobs) {
        // Sort jobs according to finish time
        Arrays.sort(jobs, Comparator.comparingInt(a -> a.finish));
        // dp[i] stores the profit for jobs till jobs[i]
        // (including jobs[i])
        int n = jobs.length;
        int[] dp = new int[n];
        dp[0] = jobs[0].profit;
        for (int i=1; i<n; i++) {
            // Profit including the current job
            int profit = jobs[i].profit;
            int l = search(jobs, i);
            if (l != -1)
                profit += dp[l];
            // Store maximum of including and excluding
            dp[i] = Math.max(profit, dp[i-1]);
        }

        return dp[n-1];
    }

    public static int search(Job[] jobs, int index) {
        int start = 0, end = index - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if(jobs[mid].finish <= jobs[index].start) {
                if (jobs[mid + 1].finish <= jobs[index].start)
                    start = mid + 1;
                else
                    return mid;
            }
            else
                end = mid - 1;
        }
        return -1;
    }
    public static void main(String[] args){
        int m = 4;
        Job[] jobs = new Job[m];
        jobs[0] = new Job(3,10,20);
        jobs[1] = new Job(1,2,50);
        jobs[2] = new Job(6,19,100);
        jobs[3] = new Job(2,100,200);
        int n = jobs.length;
        System.out.println("The optimal profit is "+search(jobs,n) );
    }

}
