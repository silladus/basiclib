# MVVM

**Android MVVM + Jetpack 架构组件**

## 设计的目标
    1.结构关系清晰明了、明确
	2.模板代码少

类的继承严格遵守3层以内，而Activity/Fragment因为Application#registerActivityLifecycleCallbacks方法的存在，可以做到无继承实现，所以其他类可能需要继承，但在Activity/Fragment上使用继承不是最好的做法（客气的说法，个人认为Activity/Fragment使用继承很low，代码很臭，因为有更好的做法（接口标记+Application#registerActivityLifecycleCallbacks））

## 使用的框架组件
    1.dagger-hilt
    2.LiveData
    3.kotlin协程
    4.Room