//
//  Book.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 6/27/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import Foundation

struct Book: Codable {
    let title: String
    let authors: [String]
    let isbn: String
    let pageCount: Int
    let fiction: Bool
}
