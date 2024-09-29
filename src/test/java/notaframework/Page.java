package notaframework;

import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract public class Page {
    protected Browser browser;
    protected InjectedContext injectedContext;
}
