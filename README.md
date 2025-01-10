# Welcome to [Slidev](https://github.com/slidevjs/slidev)!

To start the slide show:

- `npm install`
- `npm run dev`
- visit http://localhost:3030

Edit the [slides.md](./slides.md) to see the changes.

Learn more about Slidev on [documentations](https://sli.dev/).

## docker 构建 java 应用思路

- 从最简单情况着手
- 逐步向实际应用靠近，使用真实项目示例进行演示
- 再演示研究过的但现阶段项目不好落地的一些示例

### Slidev 功能体验
- 导出为 pptx 文件：
    - npx playwright install
    - slidev export --output java_docker_build --format pptx
    - 优点：间接好看
    - 缺点：动画丢失+自定义 hover 组件渲染丢失

### Roadmap

- [x] JFR + JMC examples
- [x] NativeMemoryTracking examples
- [x] deploy: github action + github pages
- [x] slidev: pagenation with theme [academic](https://github.com/alexanderdavide/slidev-theme-academic)
- [x] custome popup compoment for docker instruction examples.
