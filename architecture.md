# Architecture

## Module Dependencies

```mermaid
graph TD
    SK[shared-kernel] --&gt; D[domain]
    SK --&gt; A[application]
    SK --&gt; I[infrastructure]
    D --&gt; A
    A --&gt; I

```
![Module Graph](build/reports/module-graphgraph.png)