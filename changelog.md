Fixed crash with piston entity renderes.
This bug was much bigger as it caused vanilla rendering logic to be called way too early, so many variables were null.

Properly formatted creative item group

Fixed item rendering in crate and item duplication glitches
(Caused by us trying to get better performance than vanilla, but we realised this is not possible)

