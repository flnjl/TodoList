# TodoList
## Description
TP réalisé en cours de développement Android (natif).

L'application nécessite une connexion. Elle propose donc un écran de connexion et une interface de création de compte.

À la connexion un token de session et le nom de l'utilisateur sont enregistrés (SharedPreferences). Au redémarrage de 
l'application l'utilisateur est auto-connecté.

Une fois connecté l'écran propose une liste des tâches à faire (utilisation de SwipeRefreshLayout et RecyclerView, AsyncTask). 
La liste, gérée par un Fragment, est placée dans un TabLayout. L'utilisateur peut alors basculer vers une liste
des utilisateurs (également en Fragment).

La liste des tâches est mise à jour toutes les 10 secondes.

Lors d'un clic sur une tâche le statut (fait / à faire) de celle-ci est mis à jour (utilisation d'un Listener personnalisé 
RecyclerItemClick.) Le statut est signalé par une icône check : si l'icône est présente la tâche est réalisée.

La liste est raffraichie lors du changement de statut.

Un FloatingActionButton permet d'ajouter une tâche. La liste est mise à jour lors de l'ajout de la tâche (communication entre 
Fragment d'ajout et de liste via l'Activity).

Enfin un menu glissant situé à gauche permet de se déconnecter.

## Concepts utilisés
* Activity
* Fragment
* Ressources (string, drawable)
* AsyncTask
* View, Layout, Menu
* SharedPreferences
* SwipeRefreshLayout et RecyclerView
* TabLayout
* Communication Fragment -> Activity -> Fragment
* Material Design
