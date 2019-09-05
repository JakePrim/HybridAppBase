package datastructure.stack;

public class BrowserStack {
    //前进栈froward
    private ArrayStack<String> forwardStack = new ArrayStack<>();
    //后退栈
    private ArrayStack<String> backStack = new ArrayStack<>();

    private String currentPage;

    public void open(String url) {
        this.currentPage = url;
        forwardStack.push(url);
        System.out.println("open forwardStack:" + forwardStack);
    }

    /**
     * 后退操作
     * f：a b
     * b: d c
     *
     * @return
     */
    public boolean goBack() {
        if (forwardStack.isEmpty()) {
            System.out.println("Not Go Back");
            return false;
        }
        //将当前页面加入后退栈中
        //前进栈移除此页面
        String pop = forwardStack.pop();
        backStack.push(pop);
        showUrl(forwardStack.peek(), "Back");
        System.out.println("backStack:" + backStack);
        System.out.println("forwardStack:" + forwardStack);
        return true;
    }

    /**
     * 前进操作
     * F:a b
     * B:c
     * F:a b c
     *
     * @return
     */
    public boolean goForward() {
        if (backStack.isEmpty()) {
            System.out.println("Not Go Forward");
            return false;
        }
        String pop = backStack.pop();
        showUrl(pop, "Forward");
        forwardStack.push(pop);
        System.out.println("backStack:" + backStack);
        System.out.println("forwardStack:" + forwardStack);
        return true;
    }

    public void showUrl(String url, String p) {
        this.currentPage = url;
        System.out.println("showUrl = [" + url + "] " + p);
    }

    public void checkCurrentPage() {
        System.out.println("showUrl = [" + currentPage + "]");
    }

    public static void main(String[] args) {
        BrowserStack browser = new BrowserStack();
        browser.open("a");
        browser.open("b");
        browser.open("c");
        browser.checkCurrentPage();//c
        browser.goBack();//b
        browser.goBack();//a
        browser.goForward();//b
        browser.open("d");
        browser.goForward();//c
        browser.goBack();//d
        browser.goForward();//c
        browser.goBack();//d
        browser.goBack();//b
        browser.goBack();//a
        browser.goBack();//nul
        browser.checkCurrentPage();
        System.out.println();
    }
}
