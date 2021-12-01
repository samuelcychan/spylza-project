# spylza-project

Test application for team invite function

The project infras:
===================

[CI pipelines and apk artifacts](https://github.com/samuelcychan/spylza-project/actions).

The design strategy:
====================

- The logics to manage the permission options in Invite page, which are all implemented in
  RoleAdapter:

1. Check any open slot for new member exists. Otherwise, disable Coach, Player Coach, and Player
   options.
2. Check any open slot for new supporter exists. Otherwise, disable ReadOnly option.
3. Check if the supporter is available (non-zero limit). Otherwise, remove ReadOnly option.

- For the testcases above, mock the team data in the view model to match with the conditions. 

The project checklist:
======================

- [x] Home screen with a button to open invite screen.
- [x] UI screens, functional requirements.
- [x] Code organizations, separation of concerns.
- [x] Testability, delivery completeness.
- [x] REST api mocks with random test datasets.
- [x] Espresso UI automation testcases.


Future implementation plans:
============================

- [ ] The realistic service mock functions.
- [ ] UI stress testings to the spinner dropdown.