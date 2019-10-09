//
//  DataController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/11/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import Foundation
import CoreData

class DataController {
    var persistentContainer: NSPersistentContainer
    var shouldSeedDatabase: Bool = false

    init(completion: @escaping () -> ()) {
        // Check if the database exists.
        do {
            let databaseUrl = try FileManager.default.url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: false).appendingPathComponent("LibraryModel.sqlite")
            shouldSeedDatabase = !FileManager.default.fileExists(atPath: databaseUrl.path)
        } catch {
            shouldSeedDatabase = true
        }

        persistentContainer = NSPersistentContainer(name: "LibraryModel")
        persistentContainer.loadPersistentStores { (description, error) in
            if let error = error {
                fatalError("Core Data stack could not be loaded. \(error)")
            }

            if self.shouldSeedDatabase {
                self.seedData()
            }

            // Called once initialization of Core Data stack is complete
            DispatchQueue.main.async {
                completion()
            }
        }
    }

    private func seedData() {
        do {
            guard let rawCatalogData = try? Data(contentsOf: Bundle.main.bundleURL.appendingPathComponent("catalog.json")) else {
                return
            }
            let books = try JSONDecoder().decode([Book].self, from: rawCatalogData)
            persistentContainer.performBackgroundTask { (managedObjectContext) in
                // Loop through the books in the JSON and add to the database
                books.forEach { (book) in
                    let bookManagedObject = BookManagedObject(context: managedObjectContext)
                    bookManagedObject.title = book.title
                    bookManagedObject.authors = book.authors
                    bookManagedObject.isbn = book.isbn
                    bookManagedObject.pageCount = Int16(book.pageCount)
                    bookManagedObject.fiction = book.fiction
                }
                do {
                    try managedObjectContext.save()
                } catch {
                    print("Could not save managed object context. \(error)")
                }
            }

        } catch {
            print("Catalog.json was not found or is not decodable.")
        }

        let managedObjectContext = persistentContainer.viewContext
        let fetchRequest = NSFetchRequest<Person>(entityName: "Person")
        do {
            let persons = try managedObjectContext.fetch(fetchRequest)
        } catch {
            fatalError("Fetch could not be completed.")
        }
    }
}
