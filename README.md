## Pokedex with Kotlin Multi Platform

This is a toy project implementing a PokeDex using **Kotlin Multi Platform**. The app follows **Clean Architecture** principles with **MVVM (Model-View-ViewModel)** pattern.  
   
For the **Android native compose version** of the app, click the link below:
- [Go to PokeDex app using KMP (Compose)](https://github.com/sejun2/Pokedex-android-compose)


<table>
   <th>IOS</th>   <th>Android</th>
  <tr>
    <td style="text-align: center;">
      <img src="https://github.com/user-attachments/assets/6159fb3f-71a5-4d87-88d1-6d2391a062ba" width="300" alt="IOS">
    </td>
    <td style="text-align: center;">
      <img src="https://github.com/user-attachments/assets/f7813bc6-1623-483d-b680-90cf34d7c4f8" width="300" alt="Android">
    </td>
  </tr>
</table>


## Architecture Overview
```
┌─────────────────────────────────────────────────────────────────┐
│                           Presentation Layer                    │
│  ┌─────────────────┐    ┌──────────────┐                        │
│  │     Compose     │◄───│   ViewModel  │───────┐                │
│  │    UI Components│    │              │       │                │
│  └─────────────────┘    └──────────────┘       │                │
│                                                │                │
└────────────────────────────────────────────────│────────────────┘
                                                 │
                                                 │
                                                 │
┌────────────────────────────────────────────────│────────────────┐
│                            Domain Layer        │                │
│  ┌──────────────────┐    ┌───────────────┐    ┌▼─────────────┐  │
│  │   Domain model   │    │  Repositories │    │    UseCase   │  │
│  │                  │◄───│  (Interfaces) │◄───│              │  │
│  └──────────────────┘    └───────────────┘    └──────────────┘  │
│                                 ▲                               │
└─────────────────────────────────│───────────────────────────────┘
                                  │
                                  │
┌─────────────────────────────────│───────────────────────────────┐
│                        Data Layer│                              │
│  ┌──────────────┐    ┌───────────┴───────┐                      │
│  │  Remote Data │    │   Repositories    │                      │
│  │  Source (API)│◄───│  (Implementations)│                      │
│  └──────────────┘    └───────────────────┘                      │
└─────────────────────────────────────────────────────────────────┘
```
## Tech Stack
- **Kotlin**: Programming language
- **Coroutines**: For asynchronous programming
- **Flow**: For reactive programming
- **Ktor-Client**: HTTP client for API calls
- **Coil**: Image loading library

## Project Structure

The project is divided into three main layers:

1. **Presentation Layer**: Contains UI components (Composables) and ViewModels.
2. **Domain Layer**: Contains business logic, use cases, and repository interfaces.
3. **Data Layer**: Implements the repository interfaces and manages data sources.

## Features

- Display a list of Pokémon
- Show detailed information about each Pokémon
- Filter Pokémon list
- Fancy animations

## Limitations
 - Coil is not support GIF on KMP yet

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the app on an emulator or physical device

## Design resources
Figma: https://www.figma.com/design/3UF026k8MyMRpTeLMOv2CF/Pok%C3%A9dex-(Community)?node-id=314-3&node-type=CANVAS&t=MzsO5qUrLmBzG06H-0
