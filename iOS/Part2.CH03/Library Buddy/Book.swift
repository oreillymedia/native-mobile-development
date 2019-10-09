//
//  Book.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 6/27/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import Foundation

struct Book {
    let title: String
    let authors: [String]
    let isbn: String
    let pageCount: Int
    let fiction: Bool
}

extension Book {
    static let sampleData: [Book] = [
        Book(title: "Fight Club", authors: ["Chuck Palahniuk"], isbn: "978-0393039764", pageCount: 208, fiction: true),
        Book(title: "2001: A Space Odyssey", authors: ["Arthur C. Clarke"], isbn: "978-0451457998", pageCount: 296, fiction: true),
        Book(title: "Ulysses", authors: ["James Joyce"], isbn: "978-1420953961", pageCount: 682, fiction: true),
        Book(title: "Catch-22", authors: ["Joseph Heller"], isbn: "978-1451626650", pageCount: 544, fiction: true),
        Book(title: "The Stand", authors: ["Stephen King"], isbn: "978-0307947307", pageCount: 1200, fiction: true),
        Book(title: "On The Road", authors: ["Jack Kerouac"], isbn: "978-0143105466", pageCount: 416, fiction: true),
        Book(title: "Heart of Darkness", authors: ["Joseph Conrad"], isbn: "978-1503275928", pageCount: 78, fiction: true),
        Book(title: "A Brief History of Time", authors: ["Stephen Hawking"], isbn: "978-0553380163", pageCount: 212, fiction: false),
        Book(title: "Dispatches", authors: ["Michael Herr"], isbn: "978-0679735250", pageCount: 272, fiction: false),
        Book(title: "Harry Potter and Prisoner of Azkaban", authors: ["J.K. Rowling"], isbn: "978-0439136365", pageCount: 448, fiction: true),
        Book(title: "Dragons Love Tacos", authors: ["Adam Rubin", "Daniel Salmieri"], isbn: "978-0803736801", pageCount: 40, fiction: true),
    ]
}
