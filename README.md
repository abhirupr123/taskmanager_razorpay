# Task Manager Application

This mobile application is used to track a list of tasks for a particular user, where the user can add, update or mark any task as completed. The initial set of tasks are fetched from a sample mock API with the functionality to allow users to create custom tasks as well. Local Persistance is implemented by integerating Room Database, so that the application can work offline.

## Setup and Run

To install this application make sure to - 
- Clone this repository
- Install Android Studio
- Create a virtual device (emulator) or use a physical device to run the application

## API Integration

A third party mack website (**mocki.io**) is used to fetch a sample JSON list containing the task list. A JSON object was hosted to the API and fetched using REST API calls using Retrofit library of Android. The details of the API integration are given below - 

  ```kotlin
  private const val BASE_URL = "https://mocki.io/v1/"
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskApiService(retrofit: Retrofit): TaskApiService {
        return retrofit.create(TaskApiService::class.java)
    }
```

## Third Party Library Integration

Firebase Analytics has been added  as a third-party library to monitor the app performance, app crash analysis and track key events (Task Added, Updated and Completed). Whenever an API request is made through the App, Firebase monitors the network performance and logs the request-response time. Similarly, key events related to app are logged in the Firebase events console. Whenever the App is crashed, the crash is logged in Firebase Crashlytics with the proper stack trace.

The screenshots and crash recordings are attached below - 

### Application Crash

[Appcrash.webm](https://github.com/user-attachments/assets/6b0af515-ce0c-4bea-b3bc-6b7f2e209c8b)

<img width="1512" alt="image" src="https://github.com/user-attachments/assets/22155521-5238-4007-96bc-3cb53648eb66" />

<img width="1512" alt="image" src="https://github.com/user-attachments/assets/820bdb16-776f-4fbc-aaa1-deda502548ee" />

### Database Crash

[databasecrash.webm](https://github.com/user-attachments/assets/f8f2b028-01f7-4d96-8b67-d358ba9cca6e)

<img width="1512" alt="image" src="https://github.com/user-attachments/assets/4d40f55c-52bf-4977-bc07-df4c69fb2514" />

<img width="1512" alt="image" src="https://github.com/user-attachments/assets/9b5dc97f-0dd7-4cdb-97fd-290c2bcd0864" />

### App Crashlytics Insights

<img width="1512" alt="image" src="https://github.com/user-attachments/assets/e2b174c6-d611-4f7e-88e1-f2f8773ccfc8" />

### Firebase Events Logging

<img width="1512" alt="Pasted Graphic 1" src="https://github.com/user-attachments/assets/17bb1b16-2d93-4a22-9957-93cac783d502" />

<img width="1512" alt="Pasted Graphic 2" src="https://github.com/user-attachments/assets/e8fcb15b-8558-4fb0-ba26-1876b0871c13" />

### Application Network Peformance

<img width="1512" alt="Response time (779 ms) has not changed an" src="https://github.com/user-attachments/assets/c0fefaff-9aea-4686-82f3-bc122c5fc89a" />

## UI/UX Design

**Jetpack Compose** is used to design the UI/UX of the Application adhering to the **Material Design** patterns. The task list after being fetched from the API is being displayed in a **LazyColumn**. Each existing task can be updated or marked as completed. Along with that a **Floating Action** Button is added to allow users to add new tasks. On coompletion, updation or addition of any task, a **Toast** message is displayed at the bottom of the screen to notify users about the action.

```kotlin
Scaffold(
        topBar = { TopAppBar(title = { Text("Task Manager") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("task_detail/0")  }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = { onTaskClick(task) }, // Navigate to edit screen
                    onCompleteClick = { updatedTask ->
                        viewModel.updateTask(updatedTask) // Mark task as completed
                    },
                    onDeleteClick = { viewModel.deleteTask(task) } // Delete task
                )
            }
        }
    }
```
### Task Addition

![Screenshot_20250224_080019](https://github.com/user-attachments/assets/61d4a237-c167-4ac0-aa30-65e89238ac10)

![Screenshot_20250224_080004](https://github.com/user-attachments/assets/b306bc66-a333-459c-b17f-e9c9375aa469)


### Task Updation

![Screenshot_20250224_075818](https://github.com/user-attachments/assets/8f39a1e7-371e-47ed-9fed-2f55d1e76339)

![Screenshot_20250224_075756](https://github.com/user-attachments/assets/f2e79464-83c2-442b-a11b-6850d2fd1c54)


### Task Completion

![Screenshot_20250224_075932](https://github.com/user-attachments/assets/3336c4a4-df3d-430c-a9c3-bd6490f11ea7)











